package org.setu.focussphere.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.Duration
import java.time.LocalDateTime

@Parcelize
data class TaskModel(
    var id: Long = 0,
    var title: String = "",
    var description: String = "",
    var priorityLevel: PriorityLevel = PriorityLevel.Low,
    var status: TaskStatus = TaskStatus.ToDo,
//    var estimatedDuration: Duration? = null,
//    var actualDuration: Duration? = null,
//    var dueDate: LocalDateTime? = null 
) :Parcelable


@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable
enum class PriorityLevel {
    Low,
    Medium,
    High
}

enum class TaskStatus {
    ToDo,
    in_progress,
    Done
}
