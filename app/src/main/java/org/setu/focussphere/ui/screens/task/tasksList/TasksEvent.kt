package org.setu.focussphere.ui.screens.task.tasksList

import org.setu.focussphere.data.entities.Task

sealed class TasksEvent {
    data class OnDeleteTaskClick(val task: Task) : TasksEvent()
    data class OnDoneChange(val task: Task, val isDone: Boolean) : TasksEvent()
    object OnUndoDeleteClick : TasksEvent()
    data class OnTaskClick(val task: Task /*val taskId: Long*/) : TasksEvent()
    object OnAddTaskClick : TasksEvent()
}
