package com.app.yuru.corescheduler.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.app.yuru.corescheduler.db.entities.SleepEnhancerAudio

@Dao
interface SleepEnhancerAudioDao {
    @Query("DELETE FROM sleep_enhancer_audio")
    fun clear()

    @Query("SELECT * FROM sleep_enhancer_audio LIMIT 1")
    fun getSleepEnhancerAudio(): SleepEnhancerAudio
}