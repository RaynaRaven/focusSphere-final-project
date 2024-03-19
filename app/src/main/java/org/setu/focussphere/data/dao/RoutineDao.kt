package org.setu.focussphere.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import org.setu.focussphere.data.entities.Routine
import org.setu.focussphere.data.entities.RoutineWithTasks

@Dao
interface RoutineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutine(routine: Routine)

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

/*    TODO: add more queries for filtering routines
*/

}