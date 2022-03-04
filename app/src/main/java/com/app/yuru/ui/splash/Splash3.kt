package com.app.yuru.ui.splash

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.app.yuru.R
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import java.util.concurrent.TimeUnit

class Splash3 : AppCompatActivity() {

    private lateinit var tts_vids : VideoView
    private lateinit var splash_next_btn : ImageView
    private lateinit var playerView1: PlayerView
    var player: SimpleExoPlayer? = null
    var newUrl : String = "https://app.whyuru.com/assets/screen_video/screen_1.mp4"

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash3)

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        newUrl = intent.getStringExtra("screen3")

        splash_next_btn = findViewById(R.id.splash_next_btn3)
        splash_next_btn.setOnClickListener {
            startActivity(Intent(this, Splash4::class.java))
        }


        playerView1 = findViewById(R.id.playerView_sp3)


//        clExoPlayer = findViewById(R.id.clExoPlayer)
//        closebtndialog1 = findViewById(R.id.closebtndialog1)
//        closebtndialog1.setOnClickListener {
//            clExoPlayer.visibility = View.INVISIBLE
//            playerView1.onPause()
//            playerView1.setKeepContentOnPlayerReset(true)
//            player?.pause()
//
//        }


        click()
    //        videoPlay()

    }

    private fun videoPlay() {
        val ctlr = MediaController(this)
        ctlr.setMediaPlayer(tts_vids)

        val uri =  Uri.parse("android.resource://" + this?.getPackageName() + "/R.raw/" + R.raw.splashparts);


        tts_vids.setVideoURI(uri);

        tts_vids.setOnPreparedListener({
            it.seekTo(23000)
            it.setOnSeekCompleteListener {
                tts_vids.start()
            }
        })
//        tts_vids.setVideoPath("https://invoiz-assets.s3.amazonaws.com/hearts.mp4")


    }

    private val runnable = Runnable {
        startActivity(Intent(this, Splash4::class.java))
        finish()
    }

    override fun onResume() {
        super.onResume()
//        handler.postDelayed(runnable, TimeUnit.SECONDS.toMillis(40)) //32
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    private fun click() {

//        if (!newUrl?.equals("")!!) {
////            clExoPlayer.visibility = View.VISIBLE
//        }

        val mediaItem: MediaItem = newUrl.let { MediaItem.fromUri(it) }
        player = SimpleExoPlayer.Builder(this).build().also {
            playerView1.player = it

            playerView1.hideController()
            playerView1.setControllerVisibilityListener {
                if(it == View.VISIBLE){
                    playerView1.hideController()
                }
            }
            // Set the media item to be played.
            it.setMediaItem(mediaItem)
            // Prepare the player.
            it.prepare()
            // Start the playback.
            it.play()

            player?.volume  = 10f

        }


    }
}