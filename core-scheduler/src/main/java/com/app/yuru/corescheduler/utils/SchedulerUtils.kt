package com.app.yuru.corescheduler.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.app.yuru.corescheduler.db.SchedulerDatabase
import com.app.yuru.corescheduler.player.audio.PlayerNotificationService
import java.util.*

object SchedulerUtils {
    fun clear(context: Context) {
        SchedulerDatabase.getInstance(context).getTransitionSleepDao().clear()
        SchedulerDatabase.getInstance(context).getSleepEnhancerAudioDao().clear()
        SchedulerDatabase.getInstance(context).getSleepEnhancerVideoDao().clear()
    }

    fun scheduleNext(context: Context) {
        val transition =
            SchedulerDatabase.getInstance(context).getTransitionSleepDao().getTransitionSleep()
        transition?.let {
            val scheduledTime = it.scheduledTime
            val todayCalendar = Calendar.getInstance()
            val scheduledCalendar = Calendar.getInstance()
            scheduledCalendar.timeInMillis = scheduledTime
            scheduledCalendar.set(Calendar.YEAR, todayCalendar.get(Calendar.YEAR))
            scheduledCalendar.set(Calendar.MONTH, todayCalendar.get(Calendar.MONTH))
            scheduledCalendar.set(Calendar.DAY_OF_MONTH, todayCalendar.get(Calendar.DAY_OF_MONTH))

            if (scheduledCalendar.after(todayCalendar)) {
                val manager: AlarmManager =
                    context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(context, PlayerNotificationService::class.java)
                intent.putExtra(
                    Constants.URL,
                    "https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_5MG.mp3"
                )
                val pendingIntent =
                    when {
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                            PendingIntent.getForegroundService(
                                context,
                                System.currentTimeMillis().toInt(),
                                intent,
                                PendingIntent.FLAG_CANCEL_CURRENT
                            )
                        }
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                            PendingIntent.getService(
                                context,
                                System.currentTimeMillis().toInt(),
                                intent,
                                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
                            )
                        }
                        else -> {
                            PendingIntent.getService(
                                context,
                                System.currentTimeMillis().toInt(),
                                intent,
                                PendingIntent.FLAG_CANCEL_CURRENT
                            )
                        }
                    }
                manager.set(AlarmManager.RTC_WAKEUP, scheduledTime, pendingIntent)
                return
            }
        }
    }
}