package com.app.yuru.ui.IntroNewVideos

import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import androidx.viewpager.widget.ViewPager
import com.android.volley.RequestQueue
import com.app.yuru.R
import com.app.yuru.ui.login.LoginActivity

class ScreenTwo : AppCompatActivity() {

    private lateinit var tts_vids: VideoView
    private lateinit var skipSplash: Button
    private lateinit var splash_next_btn: ImageView
    private var requestQueue: RequestQueue? = null
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var splash_next_btn2 : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_two)

        getWindow()?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

//        splash_next_btn = findViewById(R.id.splash_next_btn2)
//        splash_next_btn.setOnClickListener {
////            viewPager.setCurrentItem(1)
//            tts_vids.pause()
//        }

        tts_vids = findViewById(R.id.tts_vids)
//        skipSplash = findViewById(R.id.skipSplash)
//
//        skipSplash.setOnClickListener {
//            startActivity(Intent(this, LoginActivity::class.java))
//            tts_vids.pause()
//        }

        splash_next_btn2 = findViewById(R.id.splash_next_btn2)
        splash_next_btn2.visibility = View.VISIBLE
        splash_next_btn2.isEnabled = false

        videoPlay()

//        tts_vids.setOnCompletionListener({
//            tts_vids.pause()
//            tts_vids.stopPlayback()
//
//            splash_next_btn2.visibility = View.VISIBLE
////            viewPager.setCurrentItem(1)
//            startActivity(Intent(this, ScreenThree::class.java))
//        })

        tts_vids.setOnCompletionListener({
            splash_next_btn2.isEnabled = true
            tts_vids.pause()
            tts_vids.stopPlayback()

//            viewPager.setCurrentItem(1)
//            startActivity(Intent(this, ScreenThree::class.java))
//            viewPager.setCurrentItem(1)
//            startActivity(Intent(this, ScreenThree::class.java))
        })


        splash_next_btn2.setOnClickListener({
            tts_vids.pause()
            tts_vids.stopPlayback()
            startActivity(Intent(this, ScreenThree::class.java))

        })

    }

    private fun videoPlay() {
        val ctlr = MediaController(this)
        ctlr.setMediaPlayer(tts_vids)
//        tts_vids.setMediaController(ctlr)

        val uri =
            Uri.parse("android.resource://" + getPackageName() + "/R.raw/" + R.raw.offline_splash);
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

    override fun onResume() {
        super.onResume()
//        handler.postDelayed(runnable, TimeUnit.SECONDS.toMillis(22))  //16
    }

    override fun onPause() {
        super.onPause()
//        handler.removeCallbacks(runnable)
        tts_vids.pause()

    }
}