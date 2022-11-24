package com.app.yuru.ui.IntroNewVideos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.VideoView
import androidx.viewpager.widget.ViewPager
import com.app.yuru.R
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import java.util.concurrent.TimeUnit

class ScreenFour : AppCompatActivity() {

    private lateinit var tts_vids: VideoView
    private lateinit var splash_next_btn: ImageView
    private lateinit var playerView1: PlayerView
    var player: SimpleExoPlayer? = null
    private lateinit var splash_next_btn4 : ImageView
    var newUrl: String = "https://app.whyuru.com/assets/screen_video/screen_3.mp4"
//    private val handler = Handler(Looper.getMainLooper())
//    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_four)

        window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
//        viewPager = requireActivity().findViewById(R.id.viewPager1)


//        splash_next_btn = view.findViewById(R.id.splash_next_btn4)
//        splash_next_btn.setOnClickListener {
//            playerView1.onPause()
//            player?.playWhenReady = false
//            player?.pause()
//            viewPager.currentItem = 3
//
//        }
        splash_next_btn4 = findViewById(R.id.splash_next_btn4)
//        tts_vids = findViewById(R.id.splash4mp4)
        playerView1 = findViewById(R.id.playerView_sp4)


        click()
//        videoPlay()

        player?.addListener(object : Player.EventListener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {

                    splash_next_btn4.visibility = View.VISIBLE
//                    startActivity(Intent(this@ScreenFour, ScreenFive::class.java))
                }
            }
        })

        splash_next_btn4.setOnClickListener({
//            tts_vids.pause()
//            tts_vids.stopPlayback()
            playerView1.onPause()
            player?.pause()
            player?.stop()
            startActivity(Intent(this, ScreenFive::class.java))
        })

    }

    override fun onResume() {
        super.onResume()
        playerView1.onPause()
        player?.playWhenReady = true
//        handler.postDelayed(runnable, TimeUnit.SECONDS.toMillis(60)) //21

    }

    override fun onPause() {
        super.onPause()
        player?.playWhenReady = false
//        handler.removeCallbacks(runnable)
    }

    private fun click() {

        val mediaItem: MediaItem = newUrl.let { MediaItem.fromUri(it) }
        player = SimpleExoPlayer.Builder(this).build().also {
            playerView1.player = it

            playerView1.hideController()
            playerView1.setControllerVisibilityListener {
                if (it == View.VISIBLE) {
                    playerView1.hideController()
                }
            }
            it.setMediaItem(mediaItem)
            it.prepare()
            it.play()
        }
    }
}