package org.setu.focussphere.main

import android.app.Application
import org.setu.focussphere.models.PriorityLevel
import org.setu.focussphere.models.TaskMemStore
import org.setu.focussphere.models.TaskModel
import org.setu.focussphere.models.TaskStatus
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp : Application() {

    val tasks = TaskMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("FocusSphere started")
    }

}