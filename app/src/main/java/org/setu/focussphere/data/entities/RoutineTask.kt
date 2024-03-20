package org.setu.focussphere.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(primaryKeys = ["routineId", "taskId"])
data class RoutineTaskCrossRef(
    var routineId: Long = 0,
    var taskId: Long = 0
)

data class RoutineWithTasks(
    @Embedded val routine: Routine,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(RoutineTaskCrossRef::class,
                parentColumn = "routineId",
                entityColumn = "taskId"
            )
    )
    val tasks: List<Task>
)