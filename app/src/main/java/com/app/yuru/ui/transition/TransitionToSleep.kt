package com.app.yuru.ui.transition


import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import java.util.concurrent.TimeUnit


class TransitionToSleep : Fragment(), ClickPosition {

    lateinit var transition_to_sleep_recy: RecyclerView
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


    private lateinit var male_tts_cl : ConstraintLayout
    private lateinit var female_tts_cl : ConstraintLayout


    private lateinit var tv45min: TextView
    private lateinit var tv90min: TextView
    private lateinit var sleep_male: TextView
    private lateinit var sleep_female: TextView
    private lateinit var tts_vids: VideoView

    var position = 0

    var isGenderCLick = "male"

    private var userId : Int = 0

    val handler = Handler(Looper.getMainLooper())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_transition_to_sleep, container, false)

        checkOnline()



//        (activity as AppCompatActivity).supportActionBar?.title = "Get to Sleep"
//        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)



        tv45min = view.findViewById(R.id.tv45min)
        tv90min = view.findViewById(R.id.tv90min)
        sleep_male = view.findViewById(R.id.sleep_male)
        sleep_female = view.findViewById(R.id.sleep_female)
        tts_vids = view.findViewById(R.id.tts_vids)

        male_tts_cl = view.findViewById(R.id.male_tts_cl)
        female_tts_cl = view.findViewById(R.id.female_tts_cl)



        videoPlay()


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

                         if(userId.equals(0)){
                             val fragment = requireActivity().supportFragmentManager.beginTransaction()
                                 .replace(R.id.framwQts, SleepEnhancerNew())
                             fragment.addToBackStack(null)
                             fragment.commit()

                             dialog.dismiss()
                         }else{
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

            if(isGenderCLick.equals("male")){
                apiVideos("male", "5min")
            }
            else{
                apiVideos("female", "5min")
            }
        }

        tv90min.setOnClickListener {

            tv45min.setTextColor(Color.GRAY)
            tv90min.setTextColor(Color.WHITE)

            if(isGenderCLick.equals("male")){
                apiVideos("male", "9min")
            }
            else{
                apiVideos("female", "9min")
            }
        }
        sleep_male.setOnClickListener {

            sleep_male.setTextColor(Color.WHITE)
            sleep_female.setTextColor(Color.GRAY)

            isGenderCLick = "male"

        }
        sleep_female.setOnClickListener {

            sleep_male.setTextColor(Color.GRAY)
            sleep_female.setTextColor(Color.WHITE)

            isGenderCLick = "female"



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


        apiVideos("male", "5min")

        return view

    }

    private fun videoPlay() {
        val ctlr = MediaController(context)
        ctlr.setMediaPlayer(tts_vids)

        val uri = Uri.parse("android.resource://" + context?.getPackageName() + "/R.raw/" + R.raw.transitionvidnew);
        tts_vids.setVideoURI(uri);
        tts_vids.start()

        tts_vids.setOnPreparedListener({ mp -> mp.isLooping = true })

    }

    private fun apiVideos(gender: String, duration: String) {

        val sh: SharedPreferences = requireActivity().getSharedPreferences("share", Context.MODE_PRIVATE)

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

                    adapterConnects()

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
                params.put("gender", gender)
                params.put("duration", duration)
                params.put("userId", ids1.toString());

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
            this
        )
        transition_to_sleep_recy.setHasFixedSize(true)
        transition_to_sleep_recy.layoutManager = GridLayoutManager(context, 2)
        transition_to_sleep_recy.adapter = transitionToSleepAdapter

    }

    private fun checkOnline() {

        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences(
            "share",
            Context.MODE_PRIVATE
        )
        val sharedNameValue : String? = sharedPreferences.getString("id", "12")

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
                params.put("userId", "12")

                return params
            }
        }
        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(stringRequest)
    }

    override fun clickPos(pos: Int) {
        position = pos
    }

}