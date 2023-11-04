package org.setu.focussphere.main

import android.app.Application
import org.setu.focussphere.models.TaskMemStore
import org.setu.focussphere.models.TaskStore
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp : Application() {

    lateinit var tasks: TaskStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        tasks = TaskMemStore()
        i("FocusSphere started")
    }

}