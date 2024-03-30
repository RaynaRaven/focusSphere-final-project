package org.setu.focussphere.ui.screens.taskTracker

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import org.setu.focussphere.R
import org.setu.focussphere.ui.screens.routine.add_edit_routine.taskSelectorModal.RoutineSelectorScreen
import org.setu.focussphere.util.Formatters
import org.setu.focussphere.util.UiEvent
import timber.log.Timber.Forest.i
import java.time.Duration

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskTrackerScreen (
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TaskTrackerViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val tasks by viewModel.tasks.collectAsState(initial = emptyList())
    val totalDuration by viewModel.totalDuration.collectAsState(initial = Duration.ZERO)
    val selectedRoutine by viewModel.selectedRoutine.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val showModal = remember { mutableStateOf(false) }
    val queuedTasks by viewModel._queuedTasks.collectAsState(initial = emptyList())
    val currentTask by viewModel.currentTask.collectAsState()

    i("current task: ${currentTask}")
    i("current duration: ${currentTask?.estimatedDuration}")
    i("current duration r: ${currentTask?.estimatedDuration?.toMillis() ?: 0L}")

//
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> { onNavigate(event) }
                else -> Unit
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = { showModal.value = true })
            {
                Text(text = stringResource(id = R.string.button_label_queue_select_routine))
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Text(modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
            text = "Current: ${currentTask?.title ?: "No tasks"}",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(1f)
        ) {
            Box( modifier = Modifier
                .weight(1.25f)
                .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.BottomCenter
            ) {
                TimerComponent(
                    currentTask = currentTask?.title ?: "",
                    currentTaskDuration = currentTask?.estimatedDuration?.toMillis() ?: 0L,
                    onTaskComplete = { viewModel.onEvent(TaskTrackerEvent.OnTimerExpired) }
                )
            }
            TimerControlButtons(onEvent = viewModel::onEvent)

            HorizontalDivider(thickness = 2.dp)
            Spacer(modifier = Modifier.height(4.dp))
            InfoRow(routineLabel = selectedRoutine?.title ?: "No Routine Selected", totalDuration = Formatters.formatDuration(totalDuration as Duration))
            LazyColumn(
                    modifier = Modifier
                        .weight(0.75f)
                        .padding(start = 16.dp, end = 12.dp)
                ) {
                    items(tasks) { task ->
                        Row(
                            modifier = Modifier.padding(2.dp)
                        ) {
                            Text(
                                modifier = Modifier.padding(end = 12.dp),
                                text = Formatters.formatDuration(task.estimatedDuration).replace(" mins", "m"),
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                modifier = Modifier.weight(1f),
                                text = task.title, style = MaterialTheme.typography.bodyLarge
                            )
                        }

                    }

                }
                if (showModal.value) {
                    Dialog(onDismissRequest = { showModal.value = false }) {
                        RoutineSelectorScreen(
                            viewModel = viewModel,
                            showModal = showModal
                        )
                    }
                }
            }
        }
}
