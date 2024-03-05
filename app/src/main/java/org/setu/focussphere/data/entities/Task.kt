package org.setu.focussphere.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class Task(
    var title: String,
//    var description: String,
//    var priorityLevel: PriorityLevel,
//    var status: TaskStatus,
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f,
    var createdDateTime: LocalDateTime,
//    var estimatedDuration: Duration?,
//    var actualDuration: Duration?,
    var isDone: Boolean,

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
)