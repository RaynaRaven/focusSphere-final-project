package org.setu.focussphere.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import org.setu.focussphere.data.entities.Routine
import org.setu.focussphere.data.entities.RoutineTaskCrossRef
import org.setu.focussphere.data.entities.RoutineWithTasks

@Dao
interface RoutineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutine(routine: Routine): Long

    @Delete
    suspend fun deleteRoutine(routine: Routine)

    @Query("SELECT * FROM routine WHERE id = :id")
    suspend fun getRoutineById(id: Long): Routine?

    //Flow datatype notifies of changes whenever there is a change in this table
    //i.e. will update this list as soon as there is another routine added
    @Query("SELECT * FROM routine")
    fun getRoutines(): Flow<List<Routine>>

    @Transaction
    @Query("SELECT * FROM routine")
    fun getRoutinesWithTasks(): Flow<List<RoutineWithTasks>>

    @Query("SELECT taskId FROM RoutineTaskCrossRef WHERE routineId = :routineId ")
    fun getTasksIdsForRoutine(routineId: Long): Flow<List<Long>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCrossRef(crossRef: RoutineTaskCrossRef)

    @Query("DELETE FROM RoutineTaskCrossRef WHERE routineId = :routineId AND taskID = :taskId")
    suspend fun deleteCrossRef(routineId: Long, taskId: Long)


}