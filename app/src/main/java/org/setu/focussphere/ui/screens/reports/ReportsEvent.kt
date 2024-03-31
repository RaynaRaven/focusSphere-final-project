package org.setu.focussphere.ui.screens.reports

sealed class ReportsEvent {

    object onDropdownTaskSelected : ReportsEvent()
    object onDropdownCategorySelected : ReportsEvent()
    object onDropdownRoutineSelected : ReportsEvent()
}