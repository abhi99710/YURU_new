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
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import java.util.concurrent.TimeUnit

class Splash5 : AppCompatActivity() {

    private lateinit var tts_vids : VideoView
    private lateinit var splash_next_btn : ImageView
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var playerView1: PlayerView
    var player: SimpleExoPlayer? = null
    var newUrl : String = "https://app.whyuru.com/assets/screen_video/screen_4.mp4"
    private var requestQueue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash5)

        val lang = Locale.getDefault().getDisplayLanguage()
        checkOnline(lang)

        Toast.makeText(this, lang, Toast.LENGTH_LONG)
//        Log.e("bb ", Locale.getDefault().getDisplayLanguage())

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        splash_next_btn = findViewById(R.id.splash_next_btn5)
        splash_next_btn.setOnClickListener {
            startActivity(Intent(this, Spalsh6::class.java))
        }

//        tts_vids = findViewById(R.id.splash5mp4)
        playerView1 = findViewById(R.id.playerView_sp5)




//        videoPlay()
    }

    private val runnable = Runnable {
        startActivity(Intent(this, Spalsh6::class.java))
        finish()
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, TimeUnit.SECONDS.toMillis(80)) //23
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    private fun click() {

        val mediaItem: MediaItem = newUrl.let { MediaItem.fromUri(it) }
        player = SimpleExoPlayer.Builder(this).build().also {
            playerView1.player = it

            playerView1.hideController()
            playerView1.setControllerVisibilityListener {
                if(it == View.VISIBLE){
                    playerView1.hideController()
                }
            }
            it.setMediaItem(mediaItem)
            it.prepare()
            it.play()
        }
    }

    private fun checkOnline(lang: String) {

        val sh: SharedPreferences =
            getSharedPreferences("share", Context.MODE_PRIVATE)

        val ids1 = sh.getString("id", "")

        val url = "https://app.whyuru.com/api/web/getscreen?language_code=en"

        val process = ProgressDialog(this)
        process.setCancelable(false)
        process.setMessage("Loading...")
        process.show()

        val stringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    process.dismiss()
                    val jsonObject = JSONObject(response)
                    val jsonObject1 = jsonObject.getJSONObject("result")
                    val data = jsonObject1.getJSONObject("data")
                    newUrl = data.getString("screen_4")

                    click()

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("language_code", lang)

                return params
            }
        }
        requestQueue = Volley.newRequestQueue(this)
        requestQueue?.add(stringRequest)
    }

}