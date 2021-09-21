package com.app.yuru.corescheduler.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.app.yuru.corescheduler.db.entities.SleepEnhancerVideo

@Dao
interface SleepEnhancerVideoDao {
    @Query("DELETE FROM sleep_enhancer_video")
    fun clear()

    @Query("SELECT * FROM sleep_enhancer_video LIMIT 1")
    fun getSleepEnhancerVideo(): SleepEnhancerVideo
}