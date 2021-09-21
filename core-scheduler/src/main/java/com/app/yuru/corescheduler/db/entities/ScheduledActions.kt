package com.app.yuru.corescheduler.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scheduled_actions")
data class ScheduledActions(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "scheduled_time")
    val scheduledTime: Long,
    @ColumnInfo(name = "status")
    val status: Int
)
