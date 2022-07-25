package com.app.yuru.ui.transition


import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.yuru.R
import com.app.yuru.corescheduler.player.video.ui.VideoActivity
import com.app.yuru.corescheduler.utils.Constants
import com.app.yuru.ui.library.LibraryModules
import com.app.yuru.ui.login.LoginActivity
import com.app.yuru.utility.apivolley.Global
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.firebase.analytics.FirebaseAnalytics
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import java.util.concurrent.TimeUnit


class TransitionToSleep : Fragment(), ClickPosition, ClickInterface, ProgramClickPosition {

    var transition_to_sleep_recy:  RecyclerView? = null
    private var requestQueue: RequestQueue? = null
    lateinit var skipToProgram: TextView
    lateinit var startTimer: TextView


    private var idC: MutableList<String> = ArrayList()
    private var fileName: MutableList<String> = ArrayList()
    private var genderL: MutableList<String> = ArrayList()
    private var languages: MutableList<String> = ArrayList()
    private var durationL: MutableList<String> = ArrayList()
    private var thumb: MutableList<String> = ArrayList()
    private var fileURL: MutableList<String> = ArrayList()


    private lateinit var male_tts_cl: ConstraintLayout
    private lateinit var female_tts_cl: ConstraintLayout

    private lateinit var logout_tts : TextView

    private lateinit var tv45min: TextView
    private lateinit var tv90min: TextView
    private lateinit var sleep_male: TextView
    private lateinit var sleep_female: TextView
    private lateinit var tts_vids: VideoView

    lateinit var exo_fullscreen_icon_tts: ImageView

    var position = 0

    lateinit var explore: TextView

    var isGenderCLick = "male"

    private var userId: Int = 0

    var genderSelected = "male"
    var durationSelected  = "5min"
    var programSelected = ""

    var checkFull = ""
    private lateinit var playerView1: PlayerView
    var player: SimpleExoPlayer? = null
    var newUrl: String = ""


    private var idforProgram: MutableList<String> = ArrayList()
    private var programName: MutableList<String> = ArrayList()

    private lateinit var rightArraow_sleep : ImageView
    private lateinit var leftArrow_sleep : ImageView

    lateinit var closeID: ImageView
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    val handler = Handler(Looper.getMainLooper())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_transition_to_sleep, container, false)



        checkOnline()


        idforProgram.add("1")
        idforProgram.add("2")
        idforProgram.add("3")
        idforProgram.add("4")

        programName.add("Program A")
        programName.add("Program B")
        programName.add("Program C")
        programName.add("Program D")



        // Obtain the FirebaseAnalytics instance.
//        firebaseAnalytics = FirebaseAnalytics()
//        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
//            param(FirebaseAnalytics.Param.ITEM_ID, id)
//            param(FirebaseAnalytics.Param.ITEM_NAME, name)
//            param(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
//        }

        rightArraow_sleep = view.findViewById(R.id.rightArraow_sleep)
        rightArraow_sleep.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.framwQts,
                SleepEnhancer2()
            ).commit()
        }

        saveGetToSleepHits()

        leftArrow_sleep = view.findViewById(R.id.leftArrow_sleep)
        leftArrow_sleep.setOnClickListener {
//            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.framwQts, AnimationOnLeft()).commit()
            val i = Intent(requireActivity(), AnimationOnLeft::class.java)
            startActivity(i)
        }

        exo_fullscreen_icon_tts = view.findViewById(R.id.exo_fullscreen_icon_tts)

        logout_tts = view.findViewById(R.id.logout_tts)
        logout_tts.setOnClickListener {
            val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor =  sharedPreferences.edit()
//            Global.ids1 = jsonObject1.getString("id")
            editor.putString("id", "")
            editor.apply()
            editor.commit()

            startActivity(Intent(context, LoginActivity::class.java))
        }




        explore = view.findViewById(R.id.explore)
        explore.setOnClickListener {
            startActivity(Intent(context, LibraryModules::class.java))
        }

//        (activity as AppCompatActivity).supportActionBar?.title = "Get to Sleep"
//        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)

        closeID = view.findViewById(R.id.closeID)
        closeID.setOnClickListener {
            player?.pause()
            closeID.visibility = View.INVISIBLE
            playerView1.visibility = View.INVISIBLE
            exo_fullscreen_icon_tts.visibility = View.INVISIBLE
        }



        tv45min = view.findViewById(R.id.tv45min)
        tv90min = view.findViewById(R.id.tv90min)
        sleep_male = view.findViewById(R.id.sleep_male)
        sleep_female = view.findViewById(R.id.sleep_female)
        tts_vids = view.findViewById(R.id.tts_vids)

        male_tts_cl = view.findViewById(R.id.male_tts_cl)
        female_tts_cl = view.findViewById(R.id.female_tts_cl)

        playerView1 = view.findViewById(R.id.playerViewtts)


//        videoPlay()

        exo_fullscreen_icon_tts.setOnClickListener {
            if (checkFull.equals("yes")) {
                player?.pause()
                closeID.visibility = View.INVISIBLE
                playerView1.visibility = View.INVISIBLE
                exo_fullscreen_icon_tts.visibility = View.INVISIBLE

                val intent = Intent(context, VideoActivity::class.java)
                intent.putExtra(
                    Constants.VIDEO_LINK,
                    newUrl
                )
                requireContext().startActivity(intent)
            }
        }

        val runnable = Runnable {

            val dialog = context?.let { Dialog(it, android.R.style.Theme_Holo_Light) }
            if (dialog != null) {
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(false)
                dialog.setContentView(R.layout.dialog_splash)
                dialog.show()

                val repeat_dialog_tts: Button = dialog.findViewById(R.id.repeat_dialog_tts)
                val sleep_dialog_tts: Button = dialog.findViewById(R.id.sleep_dialog_tts)

                repeat_dialog_tts.setOnClickListener {
                    val intent = Intent(context, VideoActivity::class.java)
                    intent.putExtra(
                        Constants.VIDEO_LINK,
                        fileURL.get(position) /*"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4"*/
                    )
                    startActivity(intent)
                    dialog.dismiss()
                }


                sleep_dialog_tts.setOnClickListener {

                    if (userId.equals(0)) {
                        val fragment = requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.framwQts, SleepEnhancerNew())
                        fragment.addToBackStack(null)
                        fragment.commit()

                        dialog.dismiss()
                    } else {
                        val fragment = requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.framwQts, SleepEnhancer())
                        fragment.addToBackStack(null)
                        fragment.commit()

                        dialog.dismiss()
                    }

                }

            }
        }


        handler.postDelayed(runnable, TimeUnit.SECONDS.toMillis(300))

        startTimer = view.findViewById(R.id.skipSleep)
        skipToProgram = view.findViewById(R.id.skipToProgram)

        startTimer.setOnClickListener {
            val fragment = requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.framwQts,
                SleepEnhancer()
            )
            fragment.addToBackStack(null)
            fragment.commit()

        }

        tv45min.setOnClickListener {

            tv90min.setTextColor(Color.GRAY)
            tv45min.setTextColor(Color.WHITE)

            if (isGenderCLick.equals("male")) {
                durationSelected = "5min"
                genderSelected = "male"
                adapterNewGet()
//                apiVideos("male", "5min")
            } else {
//                apiVideos("female", "5min")
                durationSelected = "5min"
                genderSelected = "female"
                adapterNewGet()
            }
        }

        tv90min.setOnClickListener {

            tv45min.setTextColor(Color.GRAY)
            tv90min.setTextColor(Color.WHITE)

            if (isGenderCLick.equals("male")) {
//                apiVideos("male", "9min")
                durationSelected = "9min"
                genderSelected = "male"
                adapterNewGet()
            } else {
//                apiVideos("female", "9min")
                durationSelected = "9min"
                genderSelected = "female"
                adapterNewGet()
            }
        }
        sleep_male.setOnClickListener {

            sleep_male.setTextColor(Color.WHITE)
            sleep_female.setTextColor(Color.GRAY)

            isGenderCLick = "male"

            genderSelected = "male"
        }
        sleep_female.setOnClickListener {

            sleep_male.setTextColor(Color.GRAY)
            sleep_female.setTextColor(Color.WHITE)

            isGenderCLick = "female"


            genderSelected = "female"

        }

        transition_to_sleep_recy = view.findViewById(R.id.transition_to_sleep_recy)


        skipToProgram.setOnClickListener {
            val fragment = requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.framwQts,
                WakeUpProgram()
            )
            fragment.addToBackStack(null)
            fragment.commit()
        }

        adapterNewGet()

//        apiVideos("male", "5min")

        videoPlay()
        return view

    }

    private fun adapterNewGet(){
        val transitionToSleepAdapter = NewAdapterGetToSleep(
            context,
            programName,
            idforProgram,
            genderSelected,
            durationSelected,
            this
        )
        transition_to_sleep_recy?.setHasFixedSize(true)
        transition_to_sleep_recy?.layoutManager = GridLayoutManager(context, 2)
        transition_to_sleep_recy?.adapter = transitionToSleepAdapter
    }

    private fun videoPlay() {
        val ctlr = MediaController(context)
        ctlr.setMediaPlayer(tts_vids)

        val uri =
            Uri.parse("android.resource://" + context?.getPackageName() + "/R.raw/" + R.raw.transitionvidnew);
        tts_vids.setVideoURI(uri);
        tts_vids.start()

        tts_vids.setOnPreparedListener({ mp -> mp.isLooping = true })

    }

    private fun apiVideos() {

        val sh: SharedPreferences =
            requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)

        val ids1 = sh.getString("id", "")

        val url = "http://app.whyuru.com/api/web/getAllGetToSleep"
        val process = ProgressDialog(context)
        process.setCancelable(false)
        process.setMessage("Loading...")
        process.show()

        val stringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                try {

                    process.dismiss()
                    val obj = JSONObject(response)
                    var jsonObject = obj.getJSONObject("result")
                    val jsonArray = jsonObject.getJSONArray("data")

                    idC.clear()
                    fileName.clear()
                    genderL.clear()
                    languages.clear()
                    durationL.clear()
                    thumb.clear()
                    fileURL.clear()

                    for (i in 0 until jsonArray.length()) {

                        var jsonObject1 = jsonArray.getJSONObject(i)

                        idC.add(jsonObject1.getString("id"))
                        fileName.add(jsonObject1.getString("fileName"))
                        genderL.add(jsonObject1.getString("gender"))
                        languages.add(jsonObject1.getString("languages"))
                        durationL.add(jsonObject1.getString("duration"))
                        thumb.add(jsonObject1.getString("thumb"))
                        fileURL.add(jsonObject1.getString("fileURL"))

                    }

                   newUrl = fileURL.get(0)

//                    newUrl = url.toString()
                    playerView1.visibility = View.VISIBLE
                    closeID.visibility = View.VISIBLE
                    exo_fullscreen_icon_tts.visibility = View.VISIBLE
//                    click()

                    checkFull = "yes"
                    click()
//                    adapterConnects()

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    Toast.makeText(context, volleyError.message, Toast.LENGTH_LONG).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("gender", genderSelected)
                params.put("duration", durationSelected)
                params.put("userId", ids1.toString())
                params.put("program", programSelected)

                return params
            }
        }
        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(stringRequest)
    }

    private fun adapterConnects() {
        val transitionToSleepAdapter = TtsAdapter(
            context,
            idC,
            fileName,
            genderL,
            languages,
            durationL,
            thumb,
            fileURL,
            this,
            this
        )
        transition_to_sleep_recy?.setHasFixedSize(true)
        transition_to_sleep_recy?.layoutManager = GridLayoutManager(context, 2)
        transition_to_sleep_recy?.adapter = transitionToSleepAdapter

    }

    private fun checkOnline() {

        val sh: SharedPreferences =
            requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)

        val ids1 = sh.getString("id", "")

        val url = "http://app.whyuru.com/api/web/checkForLogged"

        val process = ProgressDialog(context)
        process.setCancelable(false)
        process.setMessage("Loading...")
        process.show()

        val stringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    process.dismiss()
                    val obj = JSONObject(response)
                    var jsonObject = obj.getJSONObject("result")
                    val jsonObject1 = jsonObject.getJSONObject("data")

                    userId = jsonObject1.getInt("hasOpened")
                    Toast.makeText(context, " " + userId, Toast.LENGTH_SHORT)

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    Toast.makeText(context, volleyError.message, Toast.LENGTH_LONG).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("userId", ids1.toString())

                return params
            }
        }
        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(stringRequest)
    }

    override fun clickPos(pos: Int) {
        position = pos
    }

    private fun click() {

        val mediaItem: MediaItem = newUrl.let { MediaItem.fromUri(it) }
        player = SimpleExoPlayer.Builder(requireContext()).build().also {
            playerView1.player = it

//            playerView1.hideController()
//            playerView1.setControllerVisibilityListener {
//                if(it == View.VISIBLE){
//                    playerView1.hideController()
//                }
//            }

            it.setMediaItem(mediaItem)
            // Prepare the player.
            it.prepare()
            // Start the playback.
            it.play()

            player?.volume = 10f

        }


    }

    override fun urlGet(url: String?) {
        newUrl = url.toString()
        playerView1.visibility = View.VISIBLE
        closeID.visibility = View.VISIBLE
        exo_fullscreen_icon_tts.visibility = View.VISIBLE
        click()

        checkFull = "yes"
    }

    private fun saveGetToSleepHits() {
        val sh: SharedPreferences = requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)

        val ids1 = sh.getString("id", "")

        val url = "https://app.whyuru.com/api/web/saveGetToSleepHits"
        val process = ProgressDialog(context)
        process.setCancelable(false)
        process.setMessage("Loading...")
        process.show()

        val stringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                try {
                    process.dismiss()

                    val obj = JSONObject(response)
//                    var jsonObject = obj.getJSONObject("result")
//                    val jsonArray = jsonObject.getJSONArray("data")


                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    Toast.makeText(context, volleyError.message, Toast.LENGTH_LONG).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("userId", ids1.toString());
                params.put("getToSleepID", "1")
                return params
            }

        }
        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(stringRequest)
    }

    override fun clickIdForprogram(id: String?) {
        programSelected = id.toString()
        apiVideos()
    }

}