package org.setu.focussphere.data.repository_impl

import kotlinx.coroutines.flow.Flow
import org.setu.focussphere.data.dao.RoutineDao
import org.setu.focussphere.data.entities.Routine
import org.setu.focussphere.data.entities.RoutineTaskCrossRef
import org.setu.focussphere.data.entities.RoutineWithTasks
import org.setu.focussphere.data.repository.RoutineRepository

class RoutineRepositoryImpl(
    private val dao: RoutineDao
) : RoutineRepository {

    override suspend fun insertRoutine(routine: Routine): Long {
        return dao.insertRoutine(routine)
    }

    override suspend fun deleteRoutine(routine: Routine) {
        dao.deleteRoutine(routine)
    }

    override suspend fun getRoutineById(id: Long): Routine? {
        return dao.getRoutineById(id)
    }

    override fun getRoutines(): Flow<List<Routine>> {
        return dao.getRoutines()
    }

    override fun getRoutinesWithTasks(): Flow<List<RoutineWithTasks>> {
        return dao.getRoutinesWithTasks()
    }
    override suspend fun getTaskIdsForRoutine(routineId: Long): Flow<List<Long>> {
       return dao.getTasksIdsForRoutine(routineId)
    }

    override suspend fun insertCrossRef(routineTaskCrossRef: RoutineTaskCrossRef) {
        return dao.insertCrossRef(routineTaskCrossRef)
    }

    override suspend fun deleteCrossRef(routineTaskCrossRef: RoutineTaskCrossRef) {
        return dao.deleteCrossRef(routineTaskCrossRef.routineId, routineTaskCrossRef.taskId)
    }

}