package com.app.yuru.ui.splash

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.yuru.R
import com.app.yuru.ui.login.LoginActivity
import com.app.yuru.utility.OnSwipeTouchListner
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import java.util.concurrent.TimeUnit

class Splash2 : AppCompatActivity() {

    private lateinit var tts_vids: VideoView
//    private lateinit var skipSplash: Button
    private lateinit var splash_next_btn: ImageView
    private var requestQueue: RequestQueue? = null
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash2)

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        splash_next_btn = findViewById(R.id.splash_next_btn2)
        splash_next_btn.setOnClickListener {
            startActivity(Intent(this, Splash3::class.java))
        }

        tts_vids = findViewById(R.id.tts_vids)
//        skipSplash = findViewById(R.id.skipSplash)

//        skipSplash.setOnClickListener {
//            startActivity(Intent(this, LoginActivity::class.java))
//        }

//        checkOnline()

        videoPlay()
    }


    private fun videoPlay() {
        val ctlr = MediaController(this)
        ctlr.setMediaPlayer(tts_vids)
//        tts_vids.setMediaController(ctlr)

        val uri =
            Uri.parse("android.resource://" + this?.getPackageName() + "/R.raw/" + R.raw.offline_splash);
        //        Uri uri = Uri.parse("https://invoiz-assets.s3.amazonaws.com/hearts.mp4");

//                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/R.raw/" + R.raw.lop);
        //        Uri uri = Uri.parse("https://invoiz-assets.s3.amazonaws.com/hearts.mp4");
//        tts_vids.setMediaController(ctlr)

        //        videoView.setVideoURI(uri);

        tts_vids.setVideoURI(uri);
//        tts_vids.setVideoPath("https://invoiz-assets.s3.amazonaws.com/hearts.mp4")
        tts_vids.start()

    }

    private val runnable = Runnable {
        startActivity(Intent(this, Splash3::class.java))
        finish()
    }

    override fun onResume() {
        super.onResume()
//        handler.postDelayed(runnable, TimeUnit.SECONDS.toMillis(22))  //16
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
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