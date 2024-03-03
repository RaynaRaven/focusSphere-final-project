package org.setu.focussphere.data.enums

enum class PriorityLevel(val order: Int) {
    LOW(1),
    MEDIUM(2),
    HIGH(3)
}

enum class TaskStatus(val stringValue: String) {
    TODO("To Do"),
    IN_PROGRESS("In Progress"),
    DONE("Done")
}
