package org.setu.focussphere.ui.screens.task.add_edit_task

sealed class AddEditTaskEvent {
    data class OnTitleChanged(val title: String) : AddEditTaskEvent()
    data class OnDescriptionChanged(val description: String) : AddEditTaskEvent()
    data class OnEstimatedDurationChanged(val estimatedDuration: String) : AddEditTaskEvent()
    data class OnPriorityLevelChanged (val priorityLevel: String) : AddEditTaskEvent()
    data class OnCategoryChanged(val category: String) : AddEditTaskEvent()
    object OnSaveTaskClicked : AddEditTaskEvent()
    object OnDeleteClicked : AddEditTaskEvent()
    object OnTaskDeleted : AddEditTaskEvent()

}
