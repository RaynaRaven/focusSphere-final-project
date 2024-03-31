package org.setu.focussphere.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.setu.focussphere.data.entities.CompletionStats
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

    //Get all task completions for a particular category and date range last 7 days
//    @Query("SELECT strftime('%w', taskCompletions.completionTime / 1000, 'unixepoch') AS dayOfWeek,  categoryName FROM category WHERE categoryName = :category")
    @Query("""SELECT
            category.categoryName as "category",
            strftime("%w", taskCompletions.completionTime, "unixepoch") as "dayOfWeek",
            count(taskCompletions.id) as "numCompletions"
            FROM task 
            JOIN taskCompletions on taskCompletions.taskId=task.id
            JOIN category on task.categoryId=category.categoryId
            WHERE category.categoryId = :categoryId
            AND taskCompletions.completionTime >= strftime( "%s", "now", "weekday 1", "-7 days")
            AND taskCompletions.completionTime < strftime( "%s", "now", "weekday 0")
            GROUP BY category.categoryName, dayOfWeek;
    """)
    fun getTaskCompletionsForCategoryOverPrevSevenDays(categoryId: Long) : Flow<List<CompletionStats?>>


    //Get all task completions for a particular task
    //Get all task completions for a particular routine
    //Get all task completions for a particular category
    //Get all task completions for a particular date
    //Get all task completions for a particular date range


}