package org.setu.focussphere.data.repository

import kotlinx.coroutines.flow.Flow
import org.setu.focussphere.data.entities.Routine
import org.setu.focussphere.data.entities.RoutineTaskCrossRef
import org.setu.focussphere.data.entities.RoutineWithTasks

interface RoutineRepository {
        suspend fun insertRoutine(routine: Routine): Long

        suspend fun deleteRoutine(routine: Routine)

        suspend fun getRoutineById(id: Long): Routine?

        fun getRoutines(): Flow<List<Routine>>

        fun getRoutinesWithTasks(): Flow<List<RoutineWithTasks>>

        suspend fun getTaskIdsForRoutine(routineId: Long): Flow<List<Long>>

        suspend fun insertCrossRef(routineTaskCrossRef: RoutineTaskCrossRef)

        suspend fun deleteCrossRef(routineTaskCrossRef: RoutineTaskCrossRef)

}
