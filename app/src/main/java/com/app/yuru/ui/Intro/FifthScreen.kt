package com.app.yuru.ui.Intro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.app.yuru.R
import com.app.yuru.ui.getStarted.GetStartedActivity
import com.app.yuru.ui.login.AnimLogin
import com.app.yuru.ui.login.LoginActivity
import com.app.yuru.ui.splash.Splash7
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import java.util.concurrent.TimeUnit

class FifthScreen : Fragment() {

    private lateinit var tts_vids : VideoView
    private lateinit var splash_next_btn : ImageView
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var playerView1: PlayerView
    var player: SimpleExoPlayer? = null
    var newUrl : String = "https://app.whyuru.com/assets/screen_video/screen_1.mp4"

    lateinit var viewPager: ViewPager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fifth_screen, container, false)

        viewPager = requireActivity().findViewById(R.id.viewPager1)

        activity?.getWindow()?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        tts_vids = findViewById(R.id.splash6mp4)

        splash_next_btn = view.findViewById(R.id.splash_next_btn6)
        splash_next_btn.setOnClickListener {
            player?.pause()
            player?.playWhenReady = false
            playerView1?.onPause()
            startActivity(Intent(context, GetStartedActivity::class.java))
//            viewPager.currentItem = 5


        }

        playerView1 = view.findViewById(R.id.playerView_sp6)

        click()

        player?.addListener(object : Player.EventListener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
//                    viewPager.setCurrentItem(5)
                    startActivity(Intent(context, GetStartedActivity::class.java))
                }
            }
        })

        return view
    }

    private val runnable = Runnable {
        player?.pause()
        player?.playWhenReady = false
        playerView1?.onPause()
        startActivity(Intent(context, GetStartedActivity::class.java))

//        finish()
//        viewPager.currentItem = 5

    }

    override fun onResume() {
        super.onResume()
        player?.playWhenReady = true
        handler.postDelayed(runnable, TimeUnit.SECONDS.toMillis(100)) //22
    }

    override fun onPause() {
        super.onPause()
        player?.playWhenReady = false
        handler.removeCallbacks(runnable)
    }
    private fun click() {

        val mediaItem: MediaItem = newUrl.let { MediaItem.fromUri(it) }
        player = SimpleExoPlayer.Builder(requireContext()).build().also {
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

            player?.volume  = 0f
        }
    }
}