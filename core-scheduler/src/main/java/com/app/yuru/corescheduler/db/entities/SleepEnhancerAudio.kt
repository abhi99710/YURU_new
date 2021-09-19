package com.app.yuru.corescheduler.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sleep_enhancer_audio")
data class SleepEnhancerAudio(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "scheduled_time")
    val scheduledTime: Long,
    @ColumnInfo(name = "time_1")
    val time1: Int,
    @ColumnInfo(name = "time_2")
    val time2: Int,
    @ColumnInfo(name = "next_scheduled_time")
    val nextScheduledTime: Long,
    @ColumnInfo(name = "audio_url")
    val audioUrl: String,
    @ColumnInfo(name = "volume")
    val volume: Int,
    @ColumnInfo(name = "next_session")
    val nextSession: Int = 1
)