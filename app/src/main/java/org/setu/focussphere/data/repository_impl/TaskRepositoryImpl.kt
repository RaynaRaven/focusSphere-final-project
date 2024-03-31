package org.setu.focussphere.data.repository_impl

import kotlinx.coroutines.flow.Flow
import org.setu.focussphere.data.dao.TaskCompletionDao
import org.setu.focussphere.data.dao.TaskDao
import org.setu.focussphere.data.entities.Task
import org.setu.focussphere.data.repository.TaskRepository

class TaskRepositoryImpl(
    private val taskDao: TaskDao,
    private val tcDao: TaskCompletionDao
) : TaskRepository {

    override suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    override suspend fun getTaskById(id: Long): Task? {
        return taskDao.getTaskById(id)
    }

    override suspend fun getTasksByIds(ids: List<Long>): List<Task> {
        return taskDao.getTasksByIds(ids)
    }

    override fun getTasks(): Flow<List<Task>> {
        return taskDao.getTasks()
    }

    override fun getTasksOrderedByDateCreated(): Flow<List<Task>> {
        return taskDao.getTasksOrderedByDateCreated()
    }

    override fun getTasksOrderedByUrgencyThenByDateCreated(): Flow<List<Task>> {
        return taskDao.getTasksOrderedByUrgencyThenByDateCreated()
    }

    override fun getTasksForCategory(categoryId: Long): Flow<List<Long>> {
        return taskDao.getTasksForCategory(categoryId)
    }

    override fun getUncategorizedTasks(): Flow<List<Long>> {
        return taskDao.getUncategorizedTasks()
    }


}