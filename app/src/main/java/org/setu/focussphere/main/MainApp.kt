package org.setu.focussphere.main

import android.app.Application
import org.setu.focussphere.models.PriorityLevel
import org.setu.focussphere.models.TaskModel
import org.setu.focussphere.models.TaskStatus
import timber.log.Timber
import timber.log.Timber.Forest.i

class MainApp : Application() {

    val tasks = ArrayList<TaskModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("FocusSphere started")
        tasks.add(TaskModel("First Task", "About one...", PriorityLevel.High, TaskStatus.ToDo))
        tasks.add(TaskModel("Second Task", "About two...", PriorityLevel.Low, TaskStatus.in_progress))
        tasks.add(TaskModel("Third Task", "About three...",  PriorityLevel.Medium, TaskStatus.Done))
    }

}