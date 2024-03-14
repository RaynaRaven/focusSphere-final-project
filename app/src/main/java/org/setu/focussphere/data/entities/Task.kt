package org.setu.focussphere.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.setu.focussphere.data.enums.PriorityLevel
import org.setu.focussphere.data.enums.TaskStatus
import java.time.Duration
import java.time.LocalDateTime

@Entity
data class Task(
    var title: String = "Task Title",
    var description: String = "Task Description",
    var priorityLevel: PriorityLevel = PriorityLevel.LOW,
    var status: TaskStatus = TaskStatus.TODO,
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f,
    var createdDateTime: LocalDateTime = LocalDateTime.now(),
    var estimatedDuration: Duration? = Duration.ofMinutes(30),
    var actualDuration: Duration? = Duration.ofMinutes(0),
    var isDone: Boolean = false,

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
)

val dummyTasks = List(10) { i ->
    Task(
        title = "Dummy Task $i",
        description = "This is a dummy task $i",
        priorityLevel = PriorityLevel.HIGH,
        status = TaskStatus.TODO,
        lat = 0.0,
        lng = 0.0,
        zoom = 0f,
        createdDateTime = LocalDateTime.now(),
        estimatedDuration = Duration.ofMinutes(30),
        actualDuration = Duration.ofMinutes(0),
        isDone = false,
        id = 1234,
    )
}