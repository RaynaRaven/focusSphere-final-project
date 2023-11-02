package org.setu.focussphere.models

import org.setu.focussphere.adapters.TaskAdapter

interface TaskStore {

    fun findAll(): List<TaskModel>
    fun create(task: TaskModel)

}
