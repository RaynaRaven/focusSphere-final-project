package org.setu.focussphere.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.setu.focussphere.data.dao.RoutineDao
import org.setu.focussphere.data.dao.TaskDao
import org.setu.focussphere.data.entities.Routine
import org.setu.focussphere.data.entities.RoutineTaskCrossRef
import org.setu.focussphere.data.entities.Task
import org.setu.focussphere.helpers.Converters

@Database(
    entities = [Task::class, Routine::class, RoutineTaskCrossRef::class],
    version = 1,
    autoMigrations = [
//        AutoMigration (from = 1, to = 2),
//        AutoMigration (from = 2, to = 3)
    ]
)
@TypeConverters(Converters::class)
abstract class FocusSphereDatabase: RoomDatabase() {

    abstract fun taskDao(): TaskDao
    abstract fun routineDao(): RoutineDao
}
