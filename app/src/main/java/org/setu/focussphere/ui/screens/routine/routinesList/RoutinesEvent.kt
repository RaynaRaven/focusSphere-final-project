package org.setu.focussphere.ui.screens.routine.routinesList

import org.setu.focussphere.data.entities.Routine

sealed class RoutinesEvent {
        data class OnDeleteRoutineClick(val routine: Routine) : RoutinesEvent()
        data class OnDoneChange(val routine: Routine, val isDone: Boolean) : RoutinesEvent()
        object OnUndoDeleteClick : RoutinesEvent()
        data class OnRoutineClick(val routine: Routine) : RoutinesEvent()
        object OnAddRoutineClick : RoutinesEvent()

}
