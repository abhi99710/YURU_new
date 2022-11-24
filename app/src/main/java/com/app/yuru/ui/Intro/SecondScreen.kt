package com.app.yuru.ui.Intro

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.app.yuru.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView

class SecondScreen : Fragment() {

    private lateinit var tts_vids : VideoView
    private lateinit var splash_next_btn : ImageView
    private lateinit var playerView1: PlayerView
    var player: SimpleExoPlayer? = null
    var newUrl : String = "https://app.whyuru.com/assets/screen_video/screen_2.mp4"
    private val handler = Handler(Looper.getMainLooper())
    lateinit var viewPager: ViewPager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_second_screen, container, false)

        activity?.getWindow()?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        viewPager = requireActivity().findViewById(R.id.viewPager1)


        splash_next_btn = view.findViewById(R.id.splash_next_btn3)
        splash_next_btn.setOnClickListener {
//            startActivity(Intent(context, Splash4::class.java))
            player?.pause()
            player?.playWhenReady = false
            playerView1.onPause()
            viewPager.setCurrentItem(2)


        }


        playerView1 = view.findViewById(R.id.playerView_sp3)

        click()

        player?.addListener(object : Player.EventListener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    viewPager.setCurrentItem(2)
                }
            }
        })

        return view
    }

    private fun videoPlay() {
        val ctlr = MediaController(context)
        ctlr.setMediaPlayer(tts_vids)

        val uri =  Uri.parse("android.resource://" + context?.getPackageName() + "/R.raw/" + R.raw.splashparts);


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
//        startActivity(Intent(this, Splash4::class.java))
//        finish()
//        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.framwQts, ThirdScreen()).commit()
        player?.pause()
        player?.pause()
        player?.playWhenReady = false
        viewPager.setCurrentItem(2)
        playerView1.onPause()


    }

    override fun onResume() {
        super.onResume()
        player?.playWhenReady = true
//        handler.postDelayed(runnable, TimeUnit.SECONDS.toMillis(40)) //32
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