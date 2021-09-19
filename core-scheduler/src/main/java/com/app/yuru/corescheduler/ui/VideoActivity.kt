package com.app.yuru.corescheduler.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.yuru.corescheduler.databinding.ActivityVideoBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer


class VideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val player = SimpleExoPlayer.Builder(this).build()
        binding.playerView.player = player
        
        val videoUri = ""
        // Build the media item.
        val mediaItem: MediaItem = MediaItem.fromUri(videoUri)
        // Set the media item to be played.
        player.setMediaItem(mediaItem)
        // Prepare the player.
        player.prepare()
        // Start the playback.
        player.play()
    }
}