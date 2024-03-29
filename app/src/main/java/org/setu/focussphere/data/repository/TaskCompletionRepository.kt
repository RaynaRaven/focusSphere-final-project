package org.setu.focussphere.data.repository

import org.setu.focussphere.data.entities.TaskCompletion

interface TaskCompletionRepository {

    suspend fun insertTaskCompletion(taskCompletion: TaskCompletion) : Long
}