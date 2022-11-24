package com.app.yuru.ui.IntroNewVideos

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.app.yuru.R
import com.app.yuru.ui.Intro.FirstScreen
import com.app.yuru.ui.Intro.IntroScreens
import java.security.AccessController.getContext

class IntroductionVideoActivity : AppCompatActivity() {

    private lateinit var splash_next_btn1 : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction_video)

        val window = getWindow()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val videoLeftAnim: VideoView = findViewById<VideoView>(R.id.videoIntroduction)

        splash_next_btn1 = findViewById(R.id.splash_next_btn1)


//        videoLoginAnim = findViewById(R.id.videoLoginAnim);
        //Creating MediaController
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoLeftAnim)

        //specify the location of media file
//        Uri uri= Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/media/1.mp4");


        //specify the location of media file
//        Uri uri= Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/media/1.mp4");
        val uri =
            Uri.parse("android.resource://" + getPackageName() + "/R.raw/" + R.raw.introductoryvideo)
        //Setting MediaController and URI, then starting the videoView
//        videoLoginAnim.setMediaController(mediaController);
        //Setting MediaController and URI, then starting the videoView
//        videoLoginAnim.setMediaController(mediaController);
        videoLeftAnim.setVideoURI(uri)
        videoLeftAnim.requestFocus()
        videoLeftAnim.start()

        var duration = videoLeftAnim.duration
        Log.e("test", ""+duration)

        videoLeftAnim.setOnCompletionListener({
            startActivity(Intent(this, ScreenTwo::class.java))
//            supportFragmentManager.beginTransaction().replace(R.id.framwQts, FirstScreen()).commit()
        })

//        splash_next_btn1.setOnClickListener({
//            videoLeftAnim.pause()
//            videoLeftAnim.stopPlayback()
//            startActivity(Intent(this, ScreenTwo::class.java))
//
//        })

    }
}


