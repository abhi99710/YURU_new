package com.app.yuru.corescheduler.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.app.yuru.corescheduler.db.entities.TransitionSleep

@Dao
interface TransitionSleepDao {
    @Query("DELETE FROM transition_sleep")
    fun clear()

    @Query("SELECT * FROM transition_sleep LIMIT 1")
    fun getTransitionSleep(): TransitionSleep?
}