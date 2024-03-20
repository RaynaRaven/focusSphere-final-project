package org.setu.focussphere.ui.screens.routine.add_edit_routine

sealed class AddEditRoutineEvent {
    data class OnTitleChanged(val title: String) : AddEditRoutineEvent()
    object OnSaveRoutineClicked : AddEditRoutineEvent()
    object OnDeleteClicked : AddEditRoutineEvent()
    object OnRoutineDeleted : AddEditRoutineEvent()

}
