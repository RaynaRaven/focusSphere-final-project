package org.setu.focussphere.models

data class TaskModel(var title: String = "",
                     var description: String = "",
                     var priorityLevel: PriorityLevel = PriorityLevel.Low,
                     var status: TaskStatus = TaskStatus.ToDo
)

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
