package com.app.yuru.ui.splash

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.app.yuru.R
import java.util.concurrent.TimeUnit

class Spalsh6 : AppCompatActivity() {


    private lateinit var tts_vids : VideoView
    private lateinit var splash_next_btn : ImageView
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalsh6)

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tts_vids = findViewById(R.id.splash6mp4)

        splash_next_btn = findViewById(R.id.splash_next_btn6)
        splash_next_btn.setOnClickListener {
            startActivity(Intent(this, Splash7::class.java))
        }

        videoPlay()
    }

    private fun videoPlay() {
        val ctlr = MediaController(this)
        ctlr.setMediaPlayer(tts_vids)
//        tts_vids.setMediaController(ctlr)

        val uri =  Uri.parse("android.resource://" + this?.getPackageName() + "/R.raw/" + R.raw.splashparts);
        //        Uri uri = Uri.parse("https://invoiz-assets.s3.amazonaws.com/hearts.mp4");

//                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/R.raw/" + R.raw.lop);
        //        Uri uri = Uri.parse("https://invoiz-assets.s3.amazonaws.com/hearts.mp4");
//        tts_vids.setMediaController(ctlr)

        //        videoView.setVideoURI(uri);

        tts_vids.setVideoURI(uri);
        tts_vids.setOnPreparedListener({
            it.seekTo(83000)
            it.setOnSeekCompleteListener {
                tts_vids.start()
            }
        })
//        tts_vids.setVideoPath("https://invoiz-assets.s3.amazonaws.com/hearts.mp4")
        tts_vids.start()

    }

    private val runnable = Runnable {
        startActivity(Intent(this, Splash7::class.java))
        finish()
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, TimeUnit.SECONDS.toMillis(100)) //22
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }
}