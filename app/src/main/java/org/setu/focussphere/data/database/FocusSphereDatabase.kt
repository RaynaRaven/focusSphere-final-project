package org.setu.focussphere.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.setu.focussphere.data.dao.TaskDao
import org.setu.focussphere.data.entities.Task

@Database(
    entities = [Task::class],
    version = 1
)
abstract class FocusSphereDatabase: RoomDatabase() {

    abstract val dao: TaskDao
}
