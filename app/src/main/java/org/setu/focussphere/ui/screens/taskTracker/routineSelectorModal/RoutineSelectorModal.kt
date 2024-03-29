package org.setu.focussphere.ui.screens.routine.add_edit_routine.taskSelectorModal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.setu.focussphere.R
import org.setu.focussphere.data.entities.Routine
import org.setu.focussphere.ui.screens.taskTracker.TaskTrackerEvent
import org.setu.focussphere.ui.screens.taskTracker.TaskTrackerViewModel
import timber.log.Timber.Forest.i

@Composable
fun RoutineSelectorModal(
    routines: List<Routine>,
    viewModel: TaskTrackerViewModel,
//    selectedRoutine: Long,
//    onRoutineSelected: (Long) -> Unit,
    showModal: MutableState<Boolean>
    ) {
    Column ( modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()
        .wrapContentHeight()
        ,horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.add_edit_routine_modal_headline_select_routine),
             style = MaterialTheme.typography.headlineSmall)
        HorizontalDivider()
        LazyColumn(
            modifier = Modifier.weight(1f, fill = false)
        ){
            items(routines) { routine ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            i("Task Tracker Modal: Routine $routine clicked")
                            //TODO show snackbar here??
                            viewModel.onEvent(TaskTrackerEvent.OnRoutineClick(routine))
                            showModal.value = false
                                   },
                ){
                    Text(
                        text = routine.title,
                        style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Button (
                modifier = Modifier.align(Alignment.End),
                onClick = {
                    showModal.value = false
                }
        ) {
                Text( text = stringResource(id = R.string.button_label_done))
        }
    }
}

@Preview
@Composable
fun TaskSelectorModalPreview(){}

