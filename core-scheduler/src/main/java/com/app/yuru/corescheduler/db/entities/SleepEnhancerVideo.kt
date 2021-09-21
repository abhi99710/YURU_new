package com.app.yuru.corescheduler.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sleep_enhancer_video")
data class SleepEnhancerVideo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "scheduled_time")
    val scheduledTime: Long,
    @ColumnInfo(name = "time_1")
    val time1: Int,
    @ColumnInfo(name = "time_2")
    val time2: Int,
    @ColumnInfo(name = "video_type_1")
    val videoType1: Char,
    @ColumnInfo(name = "video_type_2")
    val videoType2: Char,
    @ColumnInfo(name = "video_url_1")
    val videoUrl1: String,
    @ColumnInfo(name = "video_url_2")
    val videoUrl2: String,
    @ColumnInfo(name = "volume")
    val volume: Int,
    @ColumnInfo(name = "next_session")
    val nextSession: Int = 1
)