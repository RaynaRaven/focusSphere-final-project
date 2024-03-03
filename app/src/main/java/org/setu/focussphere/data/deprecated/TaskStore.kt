package org.setu.focussphere.data.deprecated

import org.setu.focussphere.data.entities.Task

interface TaskStore {

    fun findAll(): List<Task>
    fun create(task: Task)

    fun update(task: Task)
    fun delete(task: Task)

    fun findById(id: Long) : Task?
}
