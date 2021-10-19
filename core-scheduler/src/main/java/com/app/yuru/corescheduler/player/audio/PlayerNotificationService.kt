package com.app.yuru.corescheduler.player.audio

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.app.yuru.corescheduler.R
import com.app.yuru.corescheduler.utils.Constants
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerNotificationManager


class PlayerNotificationService : Service() {

    private lateinit var simpleExoPlayer: SimpleExoPlayer
    private lateinit var playerNotificationManager: PlayerNotificationManager

    private var notificationId = 100001
    private var channelId = "channelId"


    override fun onCreate() {
        super.onCreate()
        simpleExoPlayer = SimpleExoPlayer.Builder(this).build()
        makeForeGround()
    }

    private fun makeForeGround() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, "Audio", NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.scheduler))
            .build()
        startForeground(notificationId, notification)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null || !intent.hasExtra(Constants.VIDEO_LINK)) {
            stopForeground(true)
            stopSelf()
        } else {
            simpleExoPlayer.setMediaItem(
                MediaItem.fromUri(
                    intent.getStringExtra(Constants.VIDEO_LINK)!!
                )
            )
            simpleExoPlayer.prepare()
            simpleExoPlayer.play()
        }
        return START_STICKY
    }

    // detach player
    override fun onDestroy() {
        playerNotificationManager.setPlayer(null)
        simpleExoPlayer.release()
        super.onDestroy()
    }
}