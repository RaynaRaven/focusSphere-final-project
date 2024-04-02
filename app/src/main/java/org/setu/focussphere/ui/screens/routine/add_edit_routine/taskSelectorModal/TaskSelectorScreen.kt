package org.setu.focussphere.ui.screens.routine.add_edit_routine.taskSelectorModal

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.setu.focussphere.ui.screens.routine.add_edit_routine.AddEditRoutineViewModel

@Composable
fun TaskSelectorScreen(
    viewModel: AddEditRoutineViewModel = hiltViewModel(),
    showModal: MutableState<Boolean>
) {
    val tasks by viewModel.tasks.collectAsState(initial = emptyList())
    val selectedTasks by viewModel.selectedTasks.collectAsState(initial = emptyList())
    Card(
        modifier = Modifier
            .sizeIn(maxHeight = 500.dp, maxWidth = 400.dp)
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        TaskSelectorModal(
            tasks = tasks,
            selectedTasks = selectedTasks,
            onTaskSelected = viewModel::toggleTaskSelection,
            showModal = showModal
        )
    }
}