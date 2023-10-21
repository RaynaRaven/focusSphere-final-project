package org.setu.focussphere.models

data class TaskModel(var title: String = "",
                     var description: String = "",
                     var priorityLevel: PriorityLevel,
                     var status: TaskStatus = TaskStatus.TO_DO
)

enum class PriorityLevel {
    HIGH,
    MEDIUM,
    LOW
}

enum class TaskStatus {
    TO_DO,
    IN_PROGRESS,
    DONE
}
