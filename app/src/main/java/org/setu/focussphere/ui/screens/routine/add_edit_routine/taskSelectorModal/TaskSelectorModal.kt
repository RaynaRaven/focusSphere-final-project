package org.setu.focussphere.ui.screens.routine.add_edit_routine.taskSelectorModal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import org.setu.focussphere.data.entities.Task

@Composable
fun TaskSelectorModal(
    tasks: List<Task>,
    selectedTasks: List<Long>,
    onTaskSelected: (Long) -> Unit,
    showModal: MutableState<Boolean>
    ) {
    Column ( modifier = Modifier.padding(16.dp)) {
        Text(text = stringResource(id = R.string.add_edit_routine_modal_headline_select_tasks),
             style = MaterialTheme.typography.headlineSmall)
        HorizontalDivider()
        LazyColumn {
            items(tasks) { task ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ){
                    Checkbox(
                             checked = selectedTasks.contains(task.id),
                             onCheckedChange = { _ ->
                                 onTaskSelected(task.id)
                             }
                    )
                    Text(text = task.title, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button (
                onClick = {
                    showModal.value = false
                }
            ) {
                Text( text = stringResource(id = R.string.fab_add_task_label))
            }
        }
    }

}

@Preview
@Composable
fun TaskSelectorModalPreview(){}

