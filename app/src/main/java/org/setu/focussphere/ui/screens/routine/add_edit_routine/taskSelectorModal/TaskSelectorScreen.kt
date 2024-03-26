package org.setu.focussphere.ui.screens.routine.add_edit_routine.taskSelectorModal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import org.setu.focussphere.ui.screens.routine.add_edit_routine.AddEditRoutineViewModel

@Composable
fun TaskSelectorScreen(
    viewModel: AddEditRoutineViewModel = hiltViewModel(),
    showModal: MutableState<Boolean>
){
    val tasks by viewModel.tasks.observeAsState(initial = emptyList())
    val selectedTasks = viewModel.selectedTasks

   TaskSelectorModal(tasks = tasks, selectedTasks = selectedTasks, onTaskSelected = viewModel::toggleTaskSelection , showModal = showModal)

}