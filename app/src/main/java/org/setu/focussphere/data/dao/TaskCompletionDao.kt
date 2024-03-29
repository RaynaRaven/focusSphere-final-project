package org.setu.focussphere.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import org.setu.focussphere.data.entities.TaskCompletion

@Dao
interface TaskCompletionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskCompletion(taskCompletion: TaskCompletion): Long
}