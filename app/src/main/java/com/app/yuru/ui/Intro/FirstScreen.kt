package com.app.yuru.ui.Intro

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.android.volley.RequestQueue
import com.app.yuru.R
import com.app.yuru.ui.login.LoginActivity
import com.app.yuru.ui.splash.Splash3

class FirstScreen : Fragment() {

    private lateinit var tts_vids: VideoView
    private lateinit var skipSplash: Button
    private lateinit var splash_next_btn: ImageView
    private var requestQueue: RequestQueue? = null
    private val handler = Handler(Looper.getMainLooper())
    lateinit var viewPager: ViewPager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first_screen, container, false)

        activity?.getWindow()?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        viewPager = requireActivity().findViewById(R.id.viewPager1)

        splash_next_btn = view.findViewById(R.id.splash_next_btn2)
        splash_next_btn.setOnClickListener {
            viewPager.setCurrentItem(1)
            tts_vids.pause()
        }

        tts_vids = view.findViewById(R.id.tts_vids)
        skipSplash = view.findViewById(R.id.skipSplash)

        skipSplash.setOnClickListener {
            startActivity(Intent(context, LoginActivity::class.java))
            tts_vids.pause()
        }

//        checkOnline()

        videoPlay()

        return view
    }

    private fun videoPlay() {
        val ctlr = MediaController(context)
        ctlr.setMediaPlayer(tts_vids)
//        tts_vids.setMediaController(ctlr)

        val uri =
            Uri.parse("android.resource://" + context?.getPackageName() + "/R.raw/" + R.raw.offline_splash);
        //        Uri uri = Uri.parse("https://invoiz-assets.s3.amazonaws.com/hearts.mp4");

//                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/R.raw/" + R.raw.lop);
        //        Uri uri = Uri.parse("https://invoiz-assets.s3.amazonaws.
        //        com/hearts.mp4");
//        tts_vids.setMediaController(ctlr)

        //        videoView.setVideoURI(uri);

        tts_vids.setVideoURI(uri);
//        tts_vids.setVideoPath("https://invoiz-assets.s3.amazonaws.com/hearts.mp4")
        tts_vids.start()

    }

    private val runnable = Runnable {
//        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.framwQts, SecondScreen()).commit()
//        context.finish()
        viewPager.setCurrentItem(1)
    }

    override fun onResume() {
        super.onResume()
//        handler.postDelayed(runnable, TimeUnit.SECONDS.toMillis(22))  //16
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
        tts_vids.pause()

    }

    /* private fun checkOnline() {

         val sh: SharedPreferences =
             applicationContext.getSharedPreferences("share", Context.MODE_PRIVATE)

         val ids1 = sh.getString("id", "")

         val url = "https://app.whyuru.com/api/web/getscreen"

         val process = ProgressDialog(this)
         process.setCancelable(false)
         process.setMessage("Loading...")
         process.show()

         val stringRequest = object : StringRequest(
             Method.GET, url,
             Response.Listener<String> { response ->
                 try {
                     process.dismiss()
                     val obj = JSONObject(response)
                     var jsonObject = obj.getJSONObject("result")
                     val jsonObject1 = jsonObject.getJSONObject("data")

                     val splashscreen3 = jsonObject1.getString("screen_1")
                     val splashscreen4 = jsonObject1.getString("screen_2")
                     val splashscreen5 = jsonObject1.getString("screen_3")
                     val splashscreen6 = jsonObject1.getString("screen_4")

                     val intent = Intent(this, Splash3::class.java)

                 } catch (e: JSONException) {
                     e.printStackTrace()
                 }
             },
             object : Response.ErrorListener {
                 override fun onErrorResponse(volleyError: VolleyError) {
                     Toast.makeText(this@Splash2, volleyError.message, Toast.LENGTH_LONG).show()
                 }
             })
         *//*{
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("userId", ids1.toString())

                return params
            }
        }*//*
        requestQueue = Volley.newRequestQueue(this)
        requestQueue?.add(stringRequest)
    }*/
}