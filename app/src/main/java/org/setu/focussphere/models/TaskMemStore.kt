package org.setu.focussphere.models

import timber.log.Timber.Forest.i

class TaskMemStore : TaskStore {

    val tasks = ArrayList<TaskModel>()

    override fun findAll(): List<TaskModel> {
        return tasks
    }

    override fun create(task: TaskModel) {
        tasks.add(task)
        logAll()
    }

    fun logAll() {
        tasks.forEach{ i("${it}") }
    }
}