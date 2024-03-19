package org.setu.focussphere.data.repository_impl

import kotlinx.coroutines.flow.Flow
import org.setu.focussphere.data.dao.RoutineDao
import org.setu.focussphere.data.entities.Routine
import org.setu.focussphere.data.entities.RoutineWithTasks
import org.setu.focussphere.data.repository.RoutineRepository

class RoutineRepositoryImpl(
    private val dao: RoutineDao
) : RoutineRepository {

    override suspend fun insertRoutine(routine: Routine) {
        dao.insertRoutine(routine)
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

}