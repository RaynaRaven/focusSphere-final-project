package org.setu.focussphere.data.repository_impl

import kotlinx.coroutines.flow.Flow
import org.setu.focussphere.data.dao.CategoryDao
import org.setu.focussphere.data.dao.TaskCompletionDao
import org.setu.focussphere.data.dao.TaskDao
import org.setu.focussphere.data.entities.CompletionStats
import org.setu.focussphere.data.entities.TaskCompletion
import org.setu.focussphere.data.repository.TaskCompletionRepository

class TaskCompletionRepositoryImpl(
    private val taskCompletionDao: TaskCompletionDao,
    private val taskDao: TaskDao,
    private val categoryDao: CategoryDao
) : TaskCompletionRepository {

    override suspend fun insertTaskCompletion(
        taskCompletion: TaskCompletion
    ) : Long {
        return taskCompletionDao.insertTaskCompletion(taskCompletion )
    }

    override suspend fun getMostRecentCompletionByTask(taskId: Long) : TaskCompletion? {
        return taskCompletionDao.getMostRecentCompletionByTask(taskId)
    }

    override fun getTaskCompletionsForCategoryOverPrevSevenDays(categoryId: Long): Flow<List<CompletionStats?>> {
        return taskCompletionDao.getTaskCompletionsForCategoryOverPrevSevenDays(categoryId)
    }

}