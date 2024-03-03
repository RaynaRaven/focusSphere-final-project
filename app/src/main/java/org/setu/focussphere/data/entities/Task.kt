package org.setu.focussphere.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.setu.focussphere.data.enums.PriorityLevel
import org.setu.focussphere.data.enums.TaskStatus
import java.time.LocalDateTime
import kotlin.time.Duration

@Entity
data class Task(
    var title: String,
    var description: String,
    var priorityLevel: PriorityLevel,
    var status: TaskStatus,
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f,
    var createdDateTime: LocalDateTime,
    var estimatedDuration: Duration?,
    var actualDuration: Duration?,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
)
