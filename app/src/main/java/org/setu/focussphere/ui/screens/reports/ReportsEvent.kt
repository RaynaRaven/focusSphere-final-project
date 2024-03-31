package org.setu.focussphere.ui.screens.reports

sealed class ReportsEvent {

    data class OnDropdownTaskSelected(val task: String) : ReportsEvent()
    data class OnDropdownCategorySelected(val categoryId: Long)  : ReportsEvent()
    object OnDropdownRoutineSelected : ReportsEvent()
}