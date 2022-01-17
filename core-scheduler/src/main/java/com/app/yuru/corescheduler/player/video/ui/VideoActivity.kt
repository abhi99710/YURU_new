package com.app.yuru.corescheduler.player.video.ui

import android.app.KeyguardManager
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.app.yuru.corescheduler.databinding.ActivityVideoBinding
import com.app.yuru.corescheduler.utils.Constants
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer


class VideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoBinding
    private var player: SimpleExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                setInheritShowWhenLocked(true)
                setShowWhenLocked(true)
                setTurnScreenOn(true)
                val keyGuardManager: KeyguardManager =
                    getSystemService(KEYGUARD_SERVICE) as KeyguardManager
                keyGuardManager.requestDismissKeyguard(this, null)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1 -> {
                setShowWhenLocked(true)
                setTurnScreenOn(true)
                val keyGuardManager: KeyguardManager =
                    getSystemService(KEYGUARD_SERVICE) as KeyguardManager
                keyGuardManager.requestDismissKeyguard(this, null)
            }
            else -> {
                window.addFlags(
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                            WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                            WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
            }
        }
        val url = intent.getStringExtra(Constants.VIDEO_LINK)
        if (url.isNullOrBlank()) {
            finish()
            return
        }
        // Build the media item.
        val mediaItem: MediaItem = MediaItem.fromUri(url)

        player = SimpleExoPlayer.Builder(this).build().also {
            binding.playerView.player = it

            // Set the media item to be played.
            it.setMediaItem(mediaItem)
            // Prepare the player.
            it.prepare()
            // Start the playback.
            it.play()
        }
    }

    override fun onStart() {
        super.onStart()
        player?.play()
    }

    override fun onStop() {
        super.onStop()
        player?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null
    }
}