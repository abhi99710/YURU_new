package com.app.yuru.ui.transition

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.*
import android.view.*
import android.widget.*
import android.window.SplashScreen
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.yuru.R
import com.app.yuru.corescheduler.player.video.ui.VideoActivity
import com.app.yuru.corescheduler.utils.Constants
import com.app.yuru.ui.library.LibraryModules
import com.app.yuru.ui.login.LoginActivity
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class TransitionToSleep : Fragment(), ClickPosition, ClickInterface, ProgramClickPosition {

    var transition_to_sleep_recy: RecyclerView? = null
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

    private lateinit var logout_tts: TextView

    private lateinit var tv45min: TextView
    private lateinit var tv90min: TextView
    private lateinit var sleep_male: TextView
    private lateinit var sleep_female: TextView
    private lateinit var tts_vids: VideoView

    var wakeupTrait = ""

    lateinit var exo_fullscreen_icon_tts: ImageView

    var position = 0

    lateinit var explore: TextView

    var isGenderCLick = "male"

    private var userId: Int = 0

    var genderSelected = "male"
    var durationSelected = "5min"
    var programSelected = ""

    var checkFull = ""
    private lateinit var playerView1: PlayerView
    var player: SimpleExoPlayer? = null
    var newUrl: String = ""

    var firstAlarm = ""
    var secondAlarm = ""
    var thirdAlarm = ""
    var forthAlarm = ""
    var trait1 = ""
    var trait2 = ""
    var dat = ""

    private var clickedImageURL = ""

    lateinit var countTimer: CountDownTimer

    private var idforProgram: MutableList<String> = ArrayList()
    private var programName: MutableList<String> = ArrayList()
    private var programPic: MutableList<String> = ArrayList()

    private lateinit var rightArraow_sleep: ImageView
    private lateinit var leftArrow_sleep: ImageView
    var listProgramImages: MutableList<String> = ArrayList()

    lateinit var closeID: ImageView
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    val handler = Handler(Looper.getMainLooper())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_transition_to_sleep, container, false)


        checkOnline()  // check user is online or not

        getThumnailForProgram()

        // add static id in list
        idforProgram.add("1")
        idforProgram.add("2")
        idforProgram.add("3")
        idforProgram.add("4")

        // add static program name in list
        programName.add("Program A")
        programName.add("Program B")
        programName.add("Program C")
        programName.add("Program D")

        // add static images in list
        programPic.add("https://projectblinkit.s3.ap-south-1.amazonaws.com/imageA.png")
        programPic.add("https://projectblinkit.s3.ap-south-1.amazonaws.com/imageB.png")
        programPic.add("https://projectblinkit.s3.ap-south-1.amazonaws.com/imageC.png")
        programPic.add("https://projectblinkit.s3.ap-south-1.amazonaws.com/imageD.png")




        getWakeUpDataSaver()

        // right arrow click
        rightArraow_sleep = view.findViewById(R.id.rightArraow_sleep)
        rightArraow_sleep.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.framwQts,
                SleepEnhancer2()
            ).commit()
        }

        saveGetToSleepHits()   // save data for screen

        // left arrow click
        leftArrow_sleep = view.findViewById(R.id.leftArrow_sleep)
        leftArrow_sleep.setOnClickListener {
//            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.framwQts, AnimationOnLeft()).commit()
            val i = Intent(requireActivity(), AnimationOnLeft::class.java)
            startActivity(i)
        }


        // logout button click
        logout_tts = view.findViewById(R.id.logout_tts)
        logout_tts.setOnClickListener {
            val sharedPreferences: SharedPreferences =
                requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
//            Global.ids1 = jsonObject1.getString("id")
            editor.putString("id", "")
            editor.apply()
            editor.commit()

            startActivity(Intent(context, SplashScreen::class.java))
        }


        // explore button for library module
        explore = view.findViewById(R.id.explore)
        explore.setOnClickListener {
            startActivity(Intent(context, LibraryModules::class.java))
        }

        // find IDs
        tv45min = view.findViewById(R.id.tv45min)
        tv90min = view.findViewById(R.id.tv90min)
        sleep_male = view.findViewById(R.id.sleep_male)
        sleep_female = view.findViewById(R.id.sleep_female)
        tts_vids = view.findViewById(R.id.tts_vids)
        male_tts_cl = view.findViewById(R.id.male_tts_cl)
        female_tts_cl = view.findViewById(R.id.female_tts_cl)
        startTimer = view.findViewById(R.id.skipSleep)
        skipToProgram = view.findViewById(R.id.skipToProgram)
        transition_to_sleep_recy = view.findViewById(R.id.transition_to_sleep_recy)

        // start timer buttton click
        startTimer.setOnClickListener {

//            dialogShow()  // calling dialog on start timer button

            dialogShowOnStartTimer()

                            val sharedPreferences: SharedPreferences =
                    requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)
                val time1 = sharedPreferences.getString("se_time1", "0")
                val time2 = sharedPreferences.getString("se_time2", "0")
                val url1 = sharedPreferences.getString("se_url", "")

                val time3 = sharedPreferences.getString("ep_time1", "0")
                val time4 = sharedPreferences.getString("ep_time2", "0")
                val url2 = sharedPreferences.getString("ep1_url", "")
                val url3 = sharedPreferences.getString("ep2_url", "")

            if(Integer.parseInt(time1) != 0){
                go(Integer.parseInt(time1), url1, "sleep1")
            }
            if(Integer.parseInt(time2) != 0){
                go(Integer.parseInt(time2 ), url1, "sleep1")
            }

//            if (url2 != null) {
                if(Integer.parseInt(time3) != 0  /*&& url2.equals("0")*/){
                    go(Integer.parseInt(time3 ), url2, "sleep_evening")
                }
//            }

//            if (url3 != null) {
                if(Integer.parseInt(time4) != 0  /*&& url3.equals("0")*/){
                    go(Integer.parseInt(time4 ), url3, "sleep_evening")
                }
//            }



        }

        // 5 min button click
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

        // 9 min button click
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

        // male button click
        sleep_male.setOnClickListener {

            sleep_male.setTextColor(Color.WHITE)
            sleep_female.setTextColor(Color.GRAY)

            isGenderCLick = "male"

            genderSelected = "male"
        }

        // female button click
        sleep_female.setOnClickListener {

            sleep_male.setTextColor(Color.GRAY)
            sleep_female.setTextColor(Color.WHITE)

            isGenderCLick = "female"


            genderSelected = "female"

        }

        // skip button click
        skipToProgram.setOnClickListener {
            val fragment = requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.framwQts,
                WakeUpProgram()
            )
            fragment.addToBackStack(null)
            fragment.commit()
        }

        adapterNewGet()  // adapter call for program list on screen

        getState()  // get saves sleep enhancer data

        getStateEvening() // get saves evening program data

//        apiVideos("male", "5min")

        videoPlay()  // call video api

        return view

    }

    //  dialog when program click
    private fun dialogShow() {
        val dialog = context?.let { Dialog(it, android.R.style.Theme_Black_NoTitleBar_Fullscreen) }
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_splash)
            dialog.show()

            val repeat_dialog_tts: Button = dialog.findViewById(R.id.repeat_dialog_tts)
            val sleep_dialog_tts: Button = dialog.findViewById(R.id.sleep_dialog_tts)

            playerView1 = dialog.findViewById(R.id.playerViewtts)
            closeID = dialog.findViewById(R.id.closeID)
            exo_fullscreen_icon_tts = dialog.findViewById(R.id.exo_fullscreen_icon_tts)

            val imageView_1: ImageView = dialog.findViewById(R.id.imageView_1)
            val imageView_2: ImageView = dialog.findViewById(R.id.imageView_2)
            val imageView_3: ImageView = dialog.findViewById(R.id.imageView_3)
            val imageView_4: ImageView = dialog.findViewById(R.id.imageView_4)

//            imageView_1.setIM =


            setImageToDashboard(imageView_3, trait1)
            setImageToDashboard(imageView_4, trait2)


            val timer: TextView = dialog.findViewById(R.id.timer)
            val tv_1: TextView = dialog.findViewById(R.id.tv_1)
            val tv_2: TextView = dialog.findViewById(R.id.tv_2)
            val tv_3: TextView = dialog.findViewById(R.id.tv_3)
            val tv_4: TextView = dialog.findViewById(R.id.tv_4)

            tv_1.text = firstAlarm
            tv_2.text = secondAlarm
            tv_3.text = thirdAlarm
            tv_4.text = forthAlarm

//            tv_1.text = trait1

            val iv_wakeup_: ImageView = dialog.findViewById(R.id.iv_wakeup_)
            val tc_wakeup_: TextView = dialog.findViewById(R.id.tc_wakeup_)
            val iv2_wakeup_: ImageView = dialog.findViewById(R.id.iv2_wakeup_)

            setImageToDashboard(iv_wakeup_, wakeupTrait)

            Picasso.get().load(clickedImageURL).fit().centerCrop().noFade().into(iv2_wakeup_)

            tc_wakeup_.text = dat


            val finishTime = player?.duration

            if (Player.STATE_READY == 3) {
                counter(timer, finishTime)
            }


            repeat_dialog_tts.setOnClickListener {
//                val intent = Intent(context, VideoActivity::class.java)
//                intent.putExtra(
//                    Constants.VIDEO_LINK,
//                    fileURL.get(position) /*"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4"*/
//                )
//                startActivity(intent)
//                dialog.dismiss()


                playerView1.onPause()
                playerView1.setKeepContentOnPlayerReset(true)
                player?.pause()

                newUrl = fileURL.get(0)
                click()

                if (countTimer == null) {
                    counter(timer, finishTime)
                } else {
                    countTimer.cancel()
                    counter(timer, finishTime)
                }
            }

            click()

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


            closeID.setOnClickListener {

//                clExoPlayer.visibility = View.INVISIBLE
                playerView1.onPause()
                playerView1.setKeepContentOnPlayerReset(true)
                player?.pause()

                dialog.dismiss()

//                player?.pause()
//                closeID.visibility = View.INVISIBLE
//                playerView1.visibility = View.INVISIBLE
//                exo_fullscreen_icon_tts.visibility = View.INVISIBLE
            }

            sleep_dialog_tts.setOnClickListener {

                if (userId.equals(0)) {
                    val fragment = requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.framwQts, SleepEnhancer())
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

    private fun dialogShowOnStartTimer() {
        val dialog = context?.let { Dialog(it, android.R.style.Theme_Black_NoTitleBar_Fullscreen) }
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialogonstarttimer)
            dialog.show()


            val constraintLayout18 : ConstraintLayout = dialog.findViewById(R.id.constraintLayout18)
            val constraintLayout19 : ConstraintLayout = dialog.findViewById(R.id.constraintLayout19)
            val constraintLayout20 : ConstraintLayout = dialog.findViewById(R.id.constraintLayout20)
            val cl_cl : ConstraintLayout = dialog.findViewById(R.id.cl_cl)

            val sleep_dialog_tts1: Button = dialog.findViewById(R.id.sleep_dialog_tts_s)
            val imageView_1: ImageView = dialog.findViewById(R.id.imageView_1s)
            val imageView_2: ImageView = dialog.findViewById(R.id.imageView_2s)
            val imageView_3: ImageView = dialog.findViewById(R.id.imageView_3s)
            val imageView_4: ImageView = dialog.findViewById(R.id.imageView_4s)

            val sleep_dialog_ttsclose : Button = dialog.findViewById(R.id.sleep_dialog_ttsclose)
            sleep_dialog_ttsclose.setOnClickListener({
                dialog.dismiss()
            })

            setImageToDashboard(imageView_3, trait1)
            setImageToDashboard(imageView_4, trait2)

            val tv_1: TextView = dialog.findViewById(R.id.tv_1s)
            val tv_2: TextView = dialog.findViewById(R.id.tv_2s)
            val tv_3: TextView = dialog.findViewById(R.id.tv_3s)
            val tv_4: TextView = dialog.findViewById(R.id.tv_4s)

            tv_1.text = firstAlarm
            tv_2.text = secondAlarm
            tv_3.text = thirdAlarm
            tv_4.text = forthAlarm


            val iv_wakeup_: ImageView = dialog.findViewById(R.id.iv_wakeup_s)
            val tc_wakeup_: TextView = dialog.findViewById(R.id.tc_wakeup_s)
            val iv2_wakeup_: ImageView = dialog.findViewById(R.id.iv2_wakeup_s)

            setImageToDashboard(iv_wakeup_, wakeupTrait)

            Picasso.get().load(clickedImageURL).fit().centerCrop().noFade().into(iv2_wakeup_)

            tc_wakeup_.text = dat

            Handler(Looper.getMainLooper()).postDelayed({
                  constraintLayout18.visibility = View.GONE
                constraintLayout19.visibility = View.GONE
                constraintLayout20.visibility = View.GONE
                cl_cl.setBackgroundColor(resources.getColor(R.color.black))
            }, 30000)

            sleep_dialog_tts1.setOnClickListener {

                val sharedPreferences: SharedPreferences =
                    requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)
                val time1 = sharedPreferences.getString("se_time1", "")
                val time2 = sharedPreferences.getString("se_time2", "")
                val url1 = sharedPreferences.getString("se_url", "")

                val time3 = sharedPreferences.getString("ep_time1", "")
                val time4 = sharedPreferences.getString("ep_time2", "")
                val url2 = sharedPreferences.getString("ep1_url", "")
                val url3 = sharedPreferences.getString("ep2_url", "")

                if(Integer.parseInt(time1) != 0){
//                    go(Integer.parseInt(time1), url1, "sleep1")
                }
                if(Integer.parseInt(time2) != 0){
//                    go(Integer.parseInt(time2), url1, "sleep1")
                }

                //            if (url2 != null) {
                if(Integer.parseInt(time3) != 0  /*&& url2.equals("0")*/){
//                    go(Integer.parseInt(time3 ), url2, "sleep_enhancer_2")
                }
//            }

//            if (url3 != null) {
                if(Integer.parseInt(time4) != 0  /*&& url3.equals("0")*/){
//                    go(Integer.parseInt(time4 ), url3, "sleep_enhancer_2")
                }



//                go(Integer.parseInt(time3), url2)
//                go(Integer.parseInt(time4), url3)

                dialog.dismiss()

            }
        }
    }

    private fun setImageToDashboard(imageView_3: ImageView, trait1: String) {

        if (trait1.equals("O")) {
            imageView_3.setImageDrawable(resources.getDrawable(R.drawable.setting_o))
        } else if (trait1.equals("C")) {
            imageView_3.setImageDrawable(resources.getDrawable(R.drawable.setting_c))
        } else if (trait1.equals("E")) {
            imageView_3.setImageDrawable(resources.getDrawable(R.drawable.setting_e))
        } else if (trait1.equals("A")) {
            imageView_3.setImageDrawable(resources.getDrawable(R.drawable.setting_a))
        } else if (trait1.equals("N")) {
            imageView_3.setImageDrawable(resources.getDrawable(R.drawable.setting_n))
        } else {
            imageView_3.setImageDrawable(resources.getDrawable(R.drawable.logo_toolbar))
        }

    }

    private fun counter(timer: TextView, finishTime: Long?) {
        var timerStart = 0L
        if (durationSelected.equals("5min")) {
            timerStart = 310000
        } else {
            timerStart = 550000
        }




        countTimer = object : CountDownTimer(timerStart, 1000) {


            // Callback function, fired on regular interval
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {

                var x = millisUntilFinished / 1000
                var y = x / 60
                if (x % 60 == 0L) {
                    if (y == -1L) {
                        y = 0L
                    } else {
                        y -= 1L
                    }
                }

                timer.text = "" + y + ":" + x % 60

            }

            // Callback function, fired
            // when the time is up
            override fun onFinish() {
                timer.text = "Timer Started"
                //                    requireActivity().supportFragmentManager.beginTransaction().replace(R.id.framwQts, SleepEnhancer()).commit()
            }
        }.start()
    }

    private fun adapterNewGet() {
        val transitionToSleepAdapter = NewAdapterGetToSleep(
            context,
            programName,
            idforProgram,
            genderSelected,
            durationSelected,
            this,
            programPic
        )
        transition_to_sleep_recy?.setHasFixedSize(true)
        transition_to_sleep_recy?.layoutManager = GridLayoutManager(context, 2)
        transition_to_sleep_recy?.adapter = transitionToSleepAdapter
    }

    private fun videoPlay() {
        val ctlr = MediaController(context)
        ctlr.setMediaPlayer(tts_vids)

        val uri =
            Uri.parse("android.resource://" + context?.packageName + "/R.raw/" + R.raw.transitionvidnew)
        tts_vids.setVideoURI(uri)
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
//                    playerView1.visibility = View.VISIBLE
//                    closeID.visibility = View.VISIBLE
//                    exo_fullscreen_icon_tts.visibility = View.VISIBLE
//                    click()

                    checkFull = "yes"
//                    click()
                    dialogShow()
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
//                    Toast.makeText(context, " " + userId, Toast.LENGTH_SHORT)

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

    override fun urlGet(url: String?, cli: String) {
        newUrl = url.toString()
        playerView1.visibility = View.VISIBLE
        closeID.visibility = View.VISIBLE
        exo_fullscreen_icon_tts.visibility = View.VISIBLE
//        click()

        dialogShow()
        checkFull = "yes"
    }

    private fun saveGetToSleepHits() {
        val sh: SharedPreferences =
            requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)

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
                params.put("userId", ids1.toString())
                params.put("getToSleepID", "1")
                return params
            }

        }
        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(stringRequest)
    }

    override fun clickIdForprogram(id: String?, position: Int) {
        programSelected = id.toString()
        apiVideos()

//        programPic.clear()

//        programPic.add(fileURL.get(position))
//        programPic.add(fileURL.get(position))
//        programPic.add(fileURL.get(position))
//        programPic.add(fileURL.get(position))
//
//        val transitionToSleepAdapter = NewAdapterGetToSleep(
//            context,
//            programName,
//            idforProgram,
//            genderSelected,
//            durationSelected,
//            this,
//            programPic
//        )
//        transition_to_sleep_recy?.setHasFixedSize(true)
//        transition_to_sleep_recy?.layoutManager = GridLayoutManager(context, 2)
//        transition_to_sleep_recy?.adapter = transitionToSleepAdapter
    }

    private fun getState() {
        val sh: SharedPreferences =
            requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)

        val ids1 = sh.getString("id", "")

        val url = "https://app.whyuru.com/api/web/getSleepEnhancerSaved?userID=" + ids1
        val process = ProgressDialog(context)
        process.setCancelable(false)
        process.setMessage("Loading...")
        process.show()

        val stringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener { response ->
                try {
                    process.dismiss()

                    val obj = JSONObject(response)
                    if (!obj.getString("status").equals("NOK")) {
                        var jsonObject = obj.getJSONObject("result")
                        var data = jsonObject.getJSONObject("data")

                        firstAlarm = data.getString("firstAlarm")
                        secondAlarm = data.getString("secondAlarm")


                    }

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

    private fun getStateEvening() {
        val sh: SharedPreferences =
            requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)

        val ids1 = sh.getString("id", "")

        val url = "https://app.whyuru.com/api/web/getSavedEveningProgram?userID=" + ids1
        val process = ProgressDialog(context)
        process.setCancelable(false)
        process.setMessage("Loading...")
        process.show()

        val stringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener { response ->
                try {
                    process.dismiss()

                    val obj = JSONObject(response)
                    if (!obj.getString("status").equals("NOK")) {
                        var jsonObject = obj.getJSONObject("result")
                        var data = jsonObject.getJSONObject("data")

                        thirdAlarm = data.getString("thirdAlarm")
                        forthAlarm = data.getString("fourthAlarm")

                        trait1 = data.getString("trait1")
                        trait2 = data.getString("trait2")
//                        trait1 = data.getString("videoURL1")
//                        trait2 = data.getString("videoURL2")
//                        trait1 = data.getString("time1")
//                        trait2 = data.getString("time2")

                    }

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

    private fun getWakeUpDataSaver() {
        val sh: SharedPreferences =
            requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)

        val ids1 = sh.getString("id", "")

        val url = "https://app.whyuru.com/api/web/getWakeUpSaved?userID=" + ids1
        val process = ProgressDialog(context)
        process.setCancelable(false)
        process.setMessage("Loading...")
//        process.show()

        val stringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener { response ->
                try {
//                    process.dismiss()

                    val obj = JSONObject(response)
                    if (obj.getBoolean("valid")) {
//                        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                        val result = obj.getJSONObject("result")
                        val data = result.getJSONObject("data")
                        dat = data.getString("time2")
                        wakeupTrait = data.getString("traits")
                        clickedImageURL = data.getString("videoURL")
                    } else {
                        Toast.makeText(context, "Not Saved", Toast.LENGTH_SHORT).show()

                    }


                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError ->
                Toast.makeText(
                    context,
                    volleyError.message,
                    Toast.LENGTH_LONG
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("userID", ids1.toString())
                return params
            }

        }
        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(stringRequest)
    }

    private fun getThumnailForProgram() {
        val sh: SharedPreferences =
            requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)

        val ids1 = sh.getString("id", "")

        val url = "https://app.whyuru.com/api/web/gts_program"
        val process = ProgressDialog(context)
        process.setCancelable(false)
        process.setMessage("Loading...")
//        process.show()

        val stringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener { response ->
                try {
//                    process.dismiss()


                    val obj = JSONObject(response)
                    if (obj.getBoolean("valid")) {
//                        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                        val result = obj.getJSONObject("result")
                        val url_images = result.getString("url")
                        val data = result.getJSONArray("data")

                        programPic.clear()
                        for (i in 0 until data.length()) {
                            val jsonObject = data.getJSONObject(i)
                            programPic.add(url_images + jsonObject.getString("imgName"))

//                            Toast.makeText(context, url_images + jsonObject.getString("imgName"), Toast.LENGTH_SHORT).show()
                        }
                        adapterNewGet()
//                        dayOfAlarm.text = data.getString("repeatDay")
                    } else {
                        Toast.makeText(context, "Not Saved", Toast.LENGTH_SHORT).show()

                    }


                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError ->
                Toast.makeText(
                    context,
                    volleyError.message,
                    Toast.LENGTH_LONG
                ).show()
            }) {

        }
        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(stringRequest)
    }

    private fun
            go(ans: Int, url: String?, fragment : String) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        val calendar = Calendar.getInstance()
        val calList: MutableList<Calendar> = ArrayList()
        calList.add(calendar)
        for (calItem in calList) {
            calItem.add(Calendar.MINUTE, ans)
            val requestCode = (calendar.timeInMillis / 1000).toInt()
            val intent = Intent(context, MyReceiver::class.java)
            intent.putExtra("REQUEST_CODE", requestCode)
            intent.putExtra("fragment", fragment)
            intent.putExtra("url", url)

            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
            val pi = PendingIntent.getBroadcast(context, requestCode, intent, 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager?.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calItem.timeInMillis,
                    pi
                )

            } else {
                alarmManager?.setExact(AlarmManager.RTC_WAKEUP, calItem.timeInMillis, pi)
//                alarmManager?.setRepeating(
//                    AlarmManager.RTC_WAKEUP,
//                    calItem.timeInMillis,
//                    24*60*60*1000,
//                    pi)
            }
            Toast.makeText(context, "Alarm has been set ", Toast.LENGTH_SHORT).show()
        }
    }

}