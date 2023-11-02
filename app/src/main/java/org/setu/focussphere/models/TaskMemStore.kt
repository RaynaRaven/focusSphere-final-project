package org.setu.focussphere.models

import timber.log.Timber.Forest.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}


class TaskMemStore : TaskStore {

    val tasks = ArrayList<TaskModel>()

    override fun findAll(): List<TaskModel> {
        return tasks
    }

    override fun create(task: TaskModel) {
        task.id = getId()
        tasks.add(task)
        logAll()
    }

    override fun update(task: TaskModel) {
        var foundTask: TaskModel? = tasks.find { t -> t.id == task.id }
        if (foundTask != null ) {
            foundTask.title = task.title
            foundTask.description = task.description
            foundTask.status = task.status
            foundTask.priorityLevel = task.priorityLevel
            logAll()
        }
    }

    fun logAll() {
        tasks.forEach{ i("${it}") }
    }
}