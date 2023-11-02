package org.setu.focussphere.models

class TaskMemStore : TaskStore {

    val tasks = ArrayList<TaskModel>()

    override fun findAll(): List<TaskModel> {
        return tasks
    }

    override fun create(task: TaskModel) {
        tasks.add(task)
    }
}