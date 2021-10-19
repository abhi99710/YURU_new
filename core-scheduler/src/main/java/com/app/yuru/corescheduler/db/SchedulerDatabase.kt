package com.app.yuru.corescheduler.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.yuru.corescheduler.db.dao.SleepEnhancerAudioDao
import com.app.yuru.corescheduler.db.dao.SleepEnhancerVideoDao
import com.app.yuru.corescheduler.db.dao.TransitionSleepDao
import com.app.yuru.corescheduler.db.entities.ScheduledActions
import com.app.yuru.corescheduler.db.entities.SleepEnhancerAudio
import com.app.yuru.corescheduler.db.entities.SleepEnhancerVideo
import com.app.yuru.corescheduler.db.entities.TransitionSleep

//@Database(
//    entities = [TransitionSleep::class, SleepEnhancerAudio::class, SleepEnhancerVideo::class, ScheduledActions::class],
//    version = 1,
//    exportSchema = true
//)
abstract class SchedulerDatabase : RoomDatabase() {

    abstract fun getTransitionSleepDao(): TransitionSleepDao

    abstract fun getSleepEnhancerAudioDao(): SleepEnhancerAudioDao

    abstract fun getSleepEnhancerVideoDao(): SleepEnhancerVideoDao

    companion object {
        private var instance: SchedulerDatabase? = null

        @Synchronized
        fun getInstance(context: Context): SchedulerDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    SchedulerDatabase::class.java,
                    "scheduler_database"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }
}