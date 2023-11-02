package org.setu.focussphere.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskModel(var id: Long = 0,
                     var title: String = "",
                     var description: String = "",
                     var priorityLevel: PriorityLevel = PriorityLevel.Low,
                     var status: TaskStatus = TaskStatus.ToDo
) :Parcelable

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
