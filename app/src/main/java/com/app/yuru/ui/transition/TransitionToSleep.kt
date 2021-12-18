package com.app.yuru.ui.transition


import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.yuru.R
import com.app.yuru.corescheduler.player.video.ui.VideoActivity
import com.app.yuru.corescheduler.utils.Constants
import com.app.yuru.utility.apivolley.APIVolley
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import java.util.concurrent.TimeUnit


class TransitionToSleep : Fragment() {

    lateinit var transition_to_sleep_recy: RecyclerView
    private var requestQueue: RequestQueue? = null
    lateinit var skipToProgram: TextView
    lateinit var startTimer: TextView
    lateinit var progressDialog: ProgressDialog

/*    private var id_45: MutableList<String> = ArrayList()
    private var language_slug: MutableList<String> = ArrayList()
    private var traint: MutableList<String> = ArrayList()
    private var gender: MutableList<String> = ArrayList()
    private var duration: MutableList<String> = ArrayList()
    private var id: MutableList<String> = ArrayList()
    private var filename: MutableList<String> = ArrayList()*/

    private var idParent45: MutableList<String> = ArrayList()
    private var language_slug45: MutableList<String> = ArrayList()
    private var title45: MutableList<String> = ArrayList()
    private var transition_id45: MutableList<String> = ArrayList()
    private var medium45: MutableList<String> = ArrayList()
    private var filename45: MutableList<String> = ArrayList()
    private var duration45: MutableList<String> = ArrayList()
    private var idChild45: MutableList<String> = ArrayList()

    // 90 sec
    private var idParent90: MutableList<String> = ArrayList()
    private var language_slug490: MutableList<String> = ArrayList()
    private var title90: MutableList<String> = ArrayList()
    private var transition_id90: MutableList<String> = ArrayList()
    private var medium90: MutableList<String> = ArrayList()
    private var filename90: MutableList<String> = ArrayList()
    private var duration90: MutableList<String> = ArrayList()
    private var idChild90: MutableList<String> = ArrayList()

    private lateinit var tv45min: TextView
    private lateinit var tv90min: TextView
    private lateinit var sleep_male: ImageView
    private lateinit var sleep_female: ImageView
    private lateinit var tts_vids: VideoView

    val handler = Handler(Looper.getMainLooper())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_transition_to_sleep, container, false)

//        Navigation.createNavigateOnClickListener(R.id.nav_host_homepage, null);

        tv45min = view.findViewById(R.id.tv45min)
        tv90min = view.findViewById(R.id.tv90min)
        sleep_male = view.findViewById(R.id.sleep_male)
        sleep_female = view.findViewById(R.id.sleep_female)
        tts_vids = view.findViewById(R.id.tts_vids)

        progressDialog = ProgressDialog(context)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loding...")
        progressDialog.show()

        videoPlay()


         val runnable = Runnable {

                 val dialog = context?.let { Dialog(it, android.R.style.Theme_Holo_Light) }
                 if (dialog != null) {
                     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                     dialog.setCancelable(true)
                     dialog.setContentView(R.layout.dialog_splash)
                     dialog.show()

//                     val recyclerView: RecyclerView = dialog.findViewById(R.id.recyclerNewSleep);

//                     val dialog_title: TextView = dialog.findViewById(R.id.dialog_title)
//                     val logo: TextView = dialog.findViewById(R.id.logo)
                     val repeat_dialog_tts: Button = dialog.findViewById(R.id.repeat_dialog_tts)
                     val sleep_dialog_tts: Button = dialog.findViewById(R.id.sleep_dialog_tts)

                     repeat_dialog_tts.setOnClickListener {
                         val intent = Intent(context, VideoActivity::class.java)
                         intent.putExtra(
                             Constants.VIDEO_LINK,
                             filename45.get(0) /*"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4"*/
                         )
                         startActivity(intent)
                         dialog.dismiss()
                     }


                     sleep_dialog_tts.setOnClickListener {
                         val fragment = requireActivity().supportFragmentManager.beginTransaction()
                             .replace(R.id.framwQts, SleepEnhancer())
                         fragment.addToBackStack(null)
                         fragment.commit()

                         dialog.dismiss()
                     }

             }
            }
        handler.postDelayed(runnable, TimeUnit.SECONDS.toMillis(300000))

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
            val transitionToSleepAdapter = TtsAdapter(
                context,
                idParent45,
                title45,
                idChild45,
                transition_id45,
                medium45,
                language_slug45,
                filename45,
                duration45,
                activity
            )
            transition_to_sleep_recy.setHasFixedSize(true)
            transition_to_sleep_recy.layoutManager = GridLayoutManager(context, 2)
            transition_to_sleep_recy.adapter = transitionToSleepAdapter

            tv45min.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
            tv90min.setBackgroundResource(R.drawable.border)
            tv90min.setTextColor(Color.BLACK)
            tv45min.setTextColor(Color.WHITE)
        }

        tv90min.setOnClickListener {
            val transitionToSleepAdapter = TtsAdapter(
                context,
                idParent90,
                title90,
                idChild90,
                transition_id90,
                medium90,
                language_slug490,
                filename90,
                duration90,
                activity
            )
            transition_to_sleep_recy.setHasFixedSize(true)
            transition_to_sleep_recy.layoutManager = GridLayoutManager(context, 2)
            transition_to_sleep_recy.adapter = transitionToSleepAdapter

            tv90min.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
            tv45min.setBackgroundResource(R.drawable.border)
            tv45min.setTextColor(Color.BLACK)
            tv90min.setTextColor(Color.WHITE)
        }
        sleep_male.setOnClickListener {
            val transitionToSleepAdapter = TtsAdapter(
                context,
                idParent45,
                title45,
                idChild45,
                transition_id45,
                medium45,
                language_slug45,
                filename45,
                duration45,
                activity
            )
            transition_to_sleep_recy.setHasFixedSize(true)
            transition_to_sleep_recy.layoutManager = GridLayoutManager(context, 2)
            transition_to_sleep_recy.adapter = transitionToSleepAdapter
        }
        sleep_female.setOnClickListener {
            val transitionToSleepAdapter = TtsAdapter(
                context,
                idParent45,
                title45,
                idChild45,
                transition_id45,
                medium45,
                language_slug45,
                filename45,
                duration45,
                activity
            )
            transition_to_sleep_recy.setHasFixedSize(true)
            transition_to_sleep_recy.layoutManager = GridLayoutManager(context, 2)
            transition_to_sleep_recy.adapter = transitionToSleepAdapter
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


        apiVideos()
        adapterConnects()


        return view

    }

    private fun videoPlay() {
        val ctlr = MediaController(context)
        ctlr.setMediaPlayer(tts_vids)
//        tts_vids.setMediaController(ctlr)

        val uri =
            Uri.parse("android.resource://" + context?.getPackageName() + "/R.raw/" + R.raw.moonset);
        //        Uri uri = Uri.parse("https://invoiz-assets.s3.amazonaws.com/hearts.mp4");

//                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/R.raw/" + R.raw.lop);
        //        Uri uri = Uri.parse("https://invoiz-assets.s3.amazonaws.com/hearts.mp4");
//        tts_vids.setMediaController(ctlr)

        //        videoView.setVideoURI(uri);

        tts_vids.setVideoURI(uri);
//        tts_vids.setVideoPath("https://invoiz-assets.s3.amazonaws.com/hearts.mp4")
        tts_vids.start()

    }

    private fun apiVideos() {
        val url = APIVolley.transition

        val stringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener<String> { response ->
                try {
                    progressDialog.dismiss()
                    val obj = JSONObject(response)
                    var jsonObject = obj.getJSONObject("result")
                    val jsonArray = jsonObject.getJSONArray("data")

                    idParent45.clear()
                    transition_id45.clear()
                    medium45.clear()
                    language_slug45.clear()
                    filename45.clear()
                    duration45.clear()
                    title45.clear()
                    idChild45.clear()


                    for (i in 0 until jsonArray.length()) {

                        var jsonObject1 = jsonArray.getJSONObject(i)


                        val jsonArrayNew = jsonObject1.getJSONArray("videos")
                        for (j in 0 until jsonArrayNew.length()) {
                            val jsonObjectNew = jsonArrayNew.getJSONObject(j)

                            if (jsonObjectNew.getString("duration").equals("45 sec")) {
                                idChild45.add(jsonObjectNew.getString("id"))
                                transition_id45.add(jsonObjectNew.getString("transition_id"))
                                medium45.add(jsonObjectNew.getString("medium"))
                                language_slug45.add(jsonObjectNew.getString("language_slug"))
                                filename45.add(jsonObjectNew.getString("filename"))
                                duration45.add(jsonObjectNew.getString("duration"))
                                idParent45.add(jsonObject1.getString("id"))
                                title45.add(jsonObject1.getString("title"))
                            } else {
                                idChild90.add(jsonObjectNew.getString("id"))
                                transition_id90.add(jsonObjectNew.getString("transition_id"))
                                medium90.add(jsonObjectNew.getString("medium"))
                                language_slug490.add(jsonObjectNew.getString("language_slug"))
                                filename90.add(jsonObjectNew.getString("filename"))
                                duration90.add(jsonObjectNew.getString("duration"))
                                idParent90.add(jsonObject1.getString("id"))
                                title90.add(jsonObject1.getString("title"))
                            }
                        }

                    }

                    adapterConnects()


                    /* if(obj.getString("msg").equals("Logged Successfully")) {

 //                        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
 //                        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
 //
 //                        editor.putString("email",jsonObject.getString("email"))
 //                        editor.apply()
 //                        editor.commit()

 //                        intent = Intent(applicationContext, Dashboard::class.java)
 //                        startActivity(intent)
                     }*/
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    Toast.makeText(context, volleyError.message, Toast.LENGTH_LONG).show()
                }
            }) {
//            @Throws(AuthFailureError::class)
//            override fun getParams(): Map<String, String> {
//                val params = HashMap<String, String>()
//                params.put("roleType", "admin")
//
//                return params
//            }
        }
        requestQueue = Volley.newRequestQueue(context)
        requestQueue?.add(stringRequest)
    }

    private fun adapterConnects() {
        val transitionToSleepAdapter = TtsAdapter(
            context,
            idParent45,
            title45,
            idChild45,
            transition_id45,
            medium45,
            language_slug45,
            filename45,
            duration45,
            activity
        )
        transition_to_sleep_recy.setHasFixedSize(true)
        transition_to_sleep_recy.layoutManager = GridLayoutManager(context, 2)
        transition_to_sleep_recy.adapter = transitionToSleepAdapter

        /* val adapterMain = AdapterMain(
             context,
             id1Female,
             category_nameFemale,
             language_nameFemale,
             genderFemale,
             traintFemale,
             durationFemale,
             url1Female
         )
         transition_to_sleep_recy?.adapter = adapterMain*/
    }

}