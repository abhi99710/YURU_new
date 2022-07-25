package com.app.yuru.ui.Intro

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
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import java.util.concurrent.TimeUnit

class ThirdScreen : Fragment() {

    private lateinit var tts_vids: VideoView
    private lateinit var splash_next_btn: ImageView
    private lateinit var playerView1: PlayerView
    var player: SimpleExoPlayer? = null
    var newUrl: String = "https://app.whyuru.com/assets/screen_video/screen_3.mp4"
    private val handler = Handler(Looper.getMainLooper())
    lateinit var viewPager: ViewPager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_third_screen, container, false)

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        viewPager = requireActivity().findViewById(R.id.viewPager1)


        splash_next_btn = view.findViewById(R.id.splash_next_btn4)
        splash_next_btn.setOnClickListener {
            playerView1.onPause()
            player?.playWhenReady = false
            player?.pause()
            viewPager.currentItem = 3

        }

//        tts_vids = findViewById(R.id.splash4mp4)
        playerView1 = view.findViewById(R.id.playerView_sp4)


        click()
//        videoPlay()

        return view
    }

    private val runnable = Runnable {
//        startActivity(Intent(this, Splash5::class.java))
//        finish()
//        requireActivity().supportFragmentManager.beginTransaction()
        player?.pause()
        player?.playWhenReady = false
        viewPager.currentItem = 3
        playerView1.onPause()


//            .replace(R.id.framwQts, ForthScreen()).commit()

    }

    override fun onResume() {
        super.onResume()
        playerView1.onPause()
        player?.playWhenReady = true
        handler.postDelayed(runnable, TimeUnit.SECONDS.toMillis(60)) //21

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