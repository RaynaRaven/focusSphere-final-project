package org.setu.focussphere.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.setu.focussphere.data.entities.Task

@Dao
interface TaskDao {

    //suspend functions run in a coroutine and are blocked until the database operation is complete

    //inserts a task if it doesn't exist and updates it if it does exist
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM task WHERE id = :id")
    suspend fun getTaskById(id: Long): Task?
    @Query("SELECT * FROM task WHERE id IN (:ids)")
    suspend fun getTasksByIds(ids: List<Long>): List<Task>

    //Flow datatype notifies you of changes whenever there is a change in this table
    //i.e. will update this list as soon as there is another task added
    @Query("SELECT * FROM task")
    fun getTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task ORDER BY createdDateTime ASC")
    fun getTasksOrderedByDateCreated(): Flow<List<Task>>

    @Query("SELECT * FROM task ORDER BY priorityLevel DESC, createdDateTime ASC")
    fun getTasksOrderedByUrgencyThenByDateCreated(): Flow<List<Task>>

    @Query("SELECT id FROM task WHERE categoryId = :categoryId")
    fun getTasksForCategory(categoryId: Long) : Flow<List<Long>>

    @Query("SELECT id FROM task WHERE categoryId IS NULL")
    fun getUncategorizedTasks(): Flow<List<Long>>

}