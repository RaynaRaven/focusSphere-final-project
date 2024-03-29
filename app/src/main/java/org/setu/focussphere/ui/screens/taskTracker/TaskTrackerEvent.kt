package org.setu.focussphere.ui.screens.taskTracker

import org.setu.focussphere.data.entities.Routine

sealed class TaskTrackerEvent {
    object OnStartClick : TaskTrackerEvent()
    object OnStopClick : TaskTrackerEvent()
    object OnDoneChange : TaskTrackerEvent()
    object OnAddRoutineClick : TaskTrackerEvent()
    data class OnRoutineClick(val routine: Routine) : TaskTrackerEvent()
}