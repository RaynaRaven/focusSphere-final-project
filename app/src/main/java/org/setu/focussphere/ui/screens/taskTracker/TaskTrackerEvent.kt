package org.setu.focussphere.ui.screens.taskTracker

sealed class TaskTrackerEvents {
    object OnStartClick : TaskTrackerEvents()
    object OnStopClick : TaskTrackerEvents()
    object OnDoneChange : TaskTrackerEvents()
    object OnRoutineClick : TaskTrackerEvents()
}