package org.setu.focussphere.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "taskCompletions",
)
data class TaskCompletion(
    var taskId: Long = 0L,
    var routineId: Long = 0L,
    var completionTime: Long = 0L,
    var duration: Long = 0L,
    var accuracy: Float = 1f,

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
)

data class CompletionStats(
    val category: String,
    val dayOfWeek: Int,
    val numCompletions: Int
)