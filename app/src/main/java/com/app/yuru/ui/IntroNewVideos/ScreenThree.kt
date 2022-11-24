package com.app.yuru.ui.IntroNewVideos


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import android.widget.VideoView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.viewpager.widget.ViewPager
import com.app.yuru.R
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView

class ScreenThree : AppCompatActivity() {

    private lateinit var tts_vids : VideoView
    private lateinit var splash_next_btn : ImageView
    private lateinit var playerView1: PlayerView
    var player: SimpleExoPlayer? = null
    private lateinit var splash_next_btn3 : ImageView
    var newUrl : String = "https://app.whyuru.com/assets/screen_video/screen_2.mp4"

//    private val handler = Handler(Looper.getMainLooper())
//    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_three)

      getWindow()?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
//        viewPager = requireActivity().findViewById(R.id.viewPager1)


//        splash_next_btn = view.findViewById(R.id.splash_next_btn3)
//        splash_next_btn.setOnClickListener {
////            startActivity(Intent(context, Splash4::class.java))
//            player?.pause()
//            player?.playWhenReady = false
//            playerView1.onPause()
////            viewPager.setCurrentItem(2)
//
//
//        }

        splash_next_btn3 = findViewById(R.id.splash_next_btn3)

        playerView1 = findViewById(R.id.playerView_sp3)

        val context = this

        click()



        player?.addListener(object : Player.EventListener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
//                    viewPager.setCurrentItem(2)
                    splash_next_btn3.visibility = View.VISIBLE
//                    startActivity(Intent(this@ScreenThree, ScreenFour::class.java))
                    Toast.makeText(this@ScreenThree, "changes", Toast.LENGTH_SHORT)
                }
            }
        })

        splash_next_btn3.setOnClickListener({
//            tts_vids.pause()
//            tts_vids.stopPlayback()
            playerView1.onPause()
            player?.pause()
            player?.stop()
            startActivity(Intent(this, ScreenFour::class.java))
        })

    }

    override fun onResume() {
        super.onResume()
        player?.playWhenReady = true
//        handler.postDelayed(runnable, TimeUnit.SECONDS.toMillis(40)) //32
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

            player?.volume  = 0f

        }


    }
}