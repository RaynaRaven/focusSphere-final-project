package org.setu.focussphere.ui.screens.routine.add_edit_routine.taskSelectorModal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import org.setu.focussphere.ui.screens.routine.add_edit_routine.AddEditRoutineViewModel

@Composable
fun TaskSelectorScreen(
    viewModel: AddEditRoutineViewModel = hiltViewModel(),
    showModal: MutableState<Boolean>
){
    val tasks by viewModel.tasks.collectAsState(initial = emptyList())
    val selectedTasks by viewModel.selectedTasks.collectAsState(initial = emptyList())

   TaskSelectorModal(tasks = tasks, selectedTasks = selectedTasks, onTaskSelected = viewModel::toggleTaskSelection , showModal = showModal)

}