package com.app.yuru.corescheduler.player.audio

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.os.IBinder
import com.app.yuru.corescheduler.player.video.ui.VideoActivity
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class PlayerNotificationService : Service() {

    private lateinit var simpleExoPlayer: SimpleExoPlayer
    private lateinit var playerNotificationManager: PlayerNotificationManager

    private var notificationId = 123;
    private var channelId = "channelId"


    override fun onCreate() {
        super.onCreate()
        val context = this
        simpleExoPlayer = SimpleExoPlayer.Builder(this).build()
        simpleExoPlayer.setMediaItem(MediaItem.fromUri(""))
        simpleExoPlayer.prepare()
        simpleExoPlayer.play()
        val builder = PlayerNotificationManager.Builder(this, notificationId, channelId)
        builder.setMediaDescriptionAdapter(object :
            PlayerNotificationManager.MediaDescriptionAdapter {
            override fun createCurrentContentIntent(player: Player): PendingIntent? {
                // return pending intent
                val intent = Intent(context, VideoActivity::class.java);
                return PendingIntent.getActivity(
                    context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
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