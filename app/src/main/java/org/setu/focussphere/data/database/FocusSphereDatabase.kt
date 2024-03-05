package org.setu.focussphere.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.setu.focussphere.data.dao.TaskDao
import org.setu.focussphere.data.entities.Task
import org.setu.focussphere.helpers.Converters

@Database(
    entities = [Task::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class FocusSphereDatabase: RoomDatabase() {

    abstract fun dao(): TaskDao
}
