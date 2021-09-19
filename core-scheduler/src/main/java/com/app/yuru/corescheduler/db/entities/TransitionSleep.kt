package com.app.yuru.corescheduler.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transition_sleep")
data class TransitionSleep(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "scheduled_time")
    val scheduledTime: Long,
    @ColumnInfo(name = "next_scheduled_time")
    val nextScheduledTime: Long,
    @ColumnInfo(name = "video_url")
    val videoUrl: String,
    @ColumnInfo(name = "volume")
    val volume: Int
)