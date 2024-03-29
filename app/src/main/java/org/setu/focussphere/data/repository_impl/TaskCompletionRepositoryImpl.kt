package org.setu.focussphere.data.repository_impl

import org.setu.focussphere.data.dao.TaskCompletionDao
import org.setu.focussphere.data.entities.TaskCompletion
import org.setu.focussphere.data.repository.TaskCompletionRepository

class TaskCompletionRepositoryImpl(
    private val dao: TaskCompletionDao
) : TaskCompletionRepository {

    override suspend fun insertTaskCompletion(
        taskCompletion: TaskCompletion
    ) : Long {
        return dao.insertTaskCompletion(taskCompletion )
    }

}