package org.setu.focussphere.models

data class TaskModel(var title: String = "",
                     var description: String = "",
                     var priorityLevel: PriorityLevel,
                     var status: TaskStatus = TaskStatus.ToDo
)

enum class PriorityLevel {
    High,
    Medium,
    Low
}

enum class TaskStatus {
    ToDo,
    in_progress,
    Done
}
