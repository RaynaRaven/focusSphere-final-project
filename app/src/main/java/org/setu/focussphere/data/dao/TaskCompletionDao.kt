package org.setu.focussphere.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.setu.focussphere.data.entities.TaskCompletion

@Dao
interface TaskCompletionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskCompletion(taskCompletion: TaskCompletion): Long

    //Generate user insights from task completions to display in reports and analytics screen

    // TODO show me the average time take for a particular task on a daily/weekly/monthly basis - output as accuracy percentage
    //TODO show me the average time take for a particular routine on a daily/weekly/monthly basis - output as accuracy percentage

    //get most recent task completion row from task completions table where there is a matching taskId
    @Query("SELECT * FROM taskCompletions JOIN task on task.id = taskCompletions.taskId WHERE task.id = :taskId ORDER BY taskCompletions.completionTime DESC LIMIT 1")
    suspend fun getMostRecentCompletionByTask(taskId: Long) : TaskCompletion?

    //Get all task completions for a particular date range
    //Get all task completions for a particular task
    //Get all task completions for a particular routine
    //Get all task completions for a particular category
    //Get all task completions for a particular date
    //Get all task completions for a particular date range


}