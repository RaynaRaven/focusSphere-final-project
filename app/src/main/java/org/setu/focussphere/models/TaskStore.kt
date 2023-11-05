package org.setu.focussphere.models

import com.google.android.gms.tasks.Task
import org.setu.focussphere.adapters.TaskAdapter

interface TaskStore {

    fun findAll(): List<TaskModel>
    fun create(task: TaskModel)

    fun update(task: TaskModel)
    fun delete(task: TaskModel)

    fun findById(id: Long) : TaskModel?
}
