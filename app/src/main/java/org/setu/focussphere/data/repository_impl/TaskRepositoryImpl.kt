package org.setu.focussphere.data.repository_impl

import kotlinx.coroutines.flow.Flow
import org.setu.focussphere.data.dao.TaskDao
import org.setu.focussphere.data.repository.TaskRepository
import org.setu.focussphere.data.entities.Task

class TaskRepositoryImpl(
    private val dao: TaskDao
) : TaskRepository {

    override suspend fun insertTask(task: Task) {
        dao.insertTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        dao.deleteTask(task)
    }

    override suspend fun getTaskById(id: Long): Task? {
        return dao.getTaskById(id)
    }

    override fun getTasks(): Flow<List<Task>> {
        return dao.getTasks()
    }

    override fun getTasksOrderedByDateCreated(): Flow<List<Task>> {
        return dao.getTasksOrderedByDateCreated()
    }

    override fun getTasksOrderedByUrgencyThenByDateCreated(): Flow<List<Task>> {
        return dao.getTasksOrderedByUrgencyThenByDateCreated()
    }
}