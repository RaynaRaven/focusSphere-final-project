package org.setu.focussphere.data.repository

import kotlinx.coroutines.flow.Flow
import org.setu.focussphere.data.entities.CompletionStats
import org.setu.focussphere.data.entities.TaskCompletion

interface TaskCompletionRepository {

    suspend fun insertTaskCompletion(taskCompletion: TaskCompletion) : Long

    suspend fun getMostRecentCompletionByTask(taskId: Long) : TaskCompletion?

    fun getTaskCompletionsForCategoryOverPrevSevenDays(categoryId: Long) : Flow<List<CompletionStats>>

    fun getLastTenTaskCompletionsForATaskId(taskId: Long) : Flow<List<TaskCompletion>>

}