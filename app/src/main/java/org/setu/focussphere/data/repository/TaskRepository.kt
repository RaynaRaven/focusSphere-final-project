package org.setu.focussphere.data.repository

import kotlinx.coroutines.flow.Flow
import org.setu.focussphere.data.entities.Task

interface TaskRepository {
    //serves as an abstraction layer between the database and the rest of the app
    //this way, the rest of the app doesn't need to know how the data is stored

    //suspend functions run in a coroutine and are blocked until the database operation is complete
    suspend fun insertTask(task: Task)

    suspend fun deleteTask(task: Task)

    suspend fun getTaskById(id: Long): Task?

    fun getTasks(): Flow<List<Task>>

    fun getTasksOrderedByDateCreated(): Flow<List<Task>>

    fun getTasksOrderedByUrgencyThenByDateCreated(): Flow<List<Task>>

    /*    TODO: add more queries for filtering tasks
            fun getTasksFilteredByCategory()
    */
}