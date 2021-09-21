package com.app.yuru.corescheduler.player.audio

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.app.yuru.corescheduler.R
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class PlayerNotificationService : Service() {

    private lateinit var simpleExoPlayer: SimpleExoPlayer
    private lateinit var playerNotificationManager: PlayerNotificationManager

    private var notificationId = 100001
    private var channelId = "channelId"


    override fun onCreate() {
        super.onCreate()
        val context = this
        simpleExoPlayer = SimpleExoPlayer.Builder(this).build()
        simpleExoPlayer.setMediaItem(MediaItem.fromUri(""))
        simpleExoPlayer.prepare()
        simpleExoPlayer.play()
        makeForeGround()
        val builder = PlayerNotificationManager.Builder(this, notificationId, channelId)
        builder.setMediaDescriptionAdapter(object :
            PlayerNotificationManager.MediaDescriptionAdapter {
            override fun createCurrentContentIntent(player: Player): PendingIntent? {
                // return pending intent
                return null
            }

            //pass description here
            override fun getCurrentContentText(player: Player): String? {
                return "Description"
            }

            //pass title (mostly playing audio name)
            override fun getCurrentContentTitle(player: Player): String {
                return "Title"
            }

            // pass image as bitmap
            override fun getCurrentLargeIcon(
                player: Player,
                callback: PlayerNotificationManager.BitmapCallback
            ): Bitmap? {
                return null
            }

        })
        playerNotificationManager =
            builder.build()
        //attach player to playerNotificationManager
        playerNotificationManager.setPlayer(simpleExoPlayer)
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
        return START_STICKY
    }

    // detach player
    override fun onDestroy() {
        playerNotificationManager.setPlayer(null)
        simpleExoPlayer.release()
        super.onDestroy()
    }

//    //removing service when user swipe out our app
//    override fun onTaskRemoved(rootIntent: Intent?) {
//        super.onTaskRemoved(rootIntent)
//        stopSelf()
//    }
}