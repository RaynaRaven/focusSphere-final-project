package org.setu.focussphere.data.repository_impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.setu.focussphere.data.dao.TaskCompletionDao
import org.setu.focussphere.data.dao.TaskDao
import org.setu.focussphere.data.entities.TaskCompletion
import org.setu.focussphere.data.entities.TaskWithAccuracy
import org.setu.focussphere.data.repository.TaskCompletionRepository
import timber.log.Timber.Forest.i

class TaskCompletionRepositoryImpl(
    private val taskCompletionDao: TaskCompletionDao,
    private val taskDao: TaskDao
) : TaskCompletionRepository {

    override suspend fun insertTaskCompletion(
        taskCompletion: TaskCompletion
    ) : Long {
        return taskCompletionDao.insertTaskCompletion(taskCompletion )
    }


    override suspend fun getMostRecentCompletionByTask(taskId: Long) : TaskCompletion? {
        return taskCompletionDao.getMostRecentCompletionByTask(taskId)
    }

}