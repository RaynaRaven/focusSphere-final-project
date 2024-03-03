package org.setu.focussphere.data.deprecated

import org.setu.focussphere.data.entities.Task
import timber.log.Timber.Forest.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}


class TaskMemStore : TaskStore {

    val tasks = ArrayList<Task>()

    override fun findAll(): List<Task> {
        return tasks
    }

    override fun create(task: Task) {
        task.id = getId()
        tasks.add(task)
        logAll()
    }

    override fun update(task: Task) {
        var foundTask: Task? = tasks.find { t -> t.id == task.id }
        if (foundTask != null ) {
            foundTask.title = task.title
            foundTask.description = task.description
            foundTask.status = task.status
            foundTask.priorityLevel = task.priorityLevel
            foundTask.lat = task.lat
            foundTask.lng = task.lng
            foundTask.zoom = task.zoom
            logAll()
        }
    }

    override fun delete(task: Task) {
        val taskToDelete = tasks.find { it.id == task.id}
        if (taskToDelete != null ) {
            tasks.remove(taskToDelete)
        }
        logAll()
    }

    override fun findById(id: Long): Task? {
        return tasks.find { it.id == id }
    }

    fun logAll() {
        tasks.forEach{ i("${it}") }
    }
}