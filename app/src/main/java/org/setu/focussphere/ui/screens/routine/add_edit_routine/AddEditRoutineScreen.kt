package org.setu.focussphere.ui.screens.routine.add_edit_routine

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.twotone.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.setu.focussphere.R
import org.setu.focussphere.ui.screens.routine.add_edit_routine.taskSelectorModal.TaskSelectorScreen
import org.setu.focussphere.util.Formatters
import org.setu.focussphere.util.Routes
import org.setu.focussphere.util.UiEvent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditRoutineScreen(
    onPopBackStack: () -> Unit,
    navController: NavController,
    viewModel: AddEditRoutineViewModel = hiltViewModel()
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val showModal = remember { mutableStateOf(false) }
    val selectedTaskIds by viewModel.selectedTasks.collectAsState(initial = emptyList())
    val selectedTasks by viewModel.getSelectedTasks(selectedTaskIds)
        .collectAsState(initial = emptyList())

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.Navigate -> navController.navigate(event.route) {
                    //adjust navigation to clear backstack to prevent back nav to addTask
                    popUpTo(Routes.DASHBOARD)
                }

                is UiEvent.ShowSnackbar -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.action
                        )
                    }
                }

                else -> Unit
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditRoutineEvent.OnSaveRoutineClicked)
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save"
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        )
        {
            //TODO UI styling for textFields
            Text(
                text = stringResource(R.string.add_edit_routine_title_label),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                label = { Text(text = stringResource(R.string.add_edit_task_textfield_label_title)) },
                value = viewModel.title,
                placeholder = { Text(text = stringResource(R.string.add_edit_routine_title_hint)) },
                onValueChange = {
                    viewModel.onEvent(AddEditRoutineEvent.OnTitleChanged(it))
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { showModal.value = true }) {
                Text(text = stringResource(id = R.string.button_label_add_tasks))
            }
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider()
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(R.string.add_edit_routine_task_list_label),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .weight(1f)
            ) {
                items(selectedTasks) { task ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier.padding(end = 12.dp),
                            text = Formatters.formatDuration(task.estimatedDuration)
                                .replace(" mins", "m"),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = task.title, style = MaterialTheme.typography.bodyLarge
                        )
                        IconButton(onClick = { /* TODO Move Up*/ }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowUp,
                                contentDescription = "Move item up"
                            )
                        }
                        IconButton(onClick = { /* TODO Move Down*/ }) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Move item down"
                            )
                        }
                        IconButton(onClick = { /* TODO Delete*/ }) {
                            Icon(
                                imageVector = Icons.TwoTone.Clear,
                                contentDescription = "Delete item"
                            )
                        }
                    }
                    HorizontalDivider(Modifier.fillMaxWidth(), color = Color.Gray, thickness = 1.dp)
                }
            }
            if (showModal.value) {
                Dialog(onDismissRequest = { showModal.value = false }) {
                    TaskSelectorScreen(
                        viewModel = viewModel,
                        showModal = showModal
                    )
                }
            }
        }
    }
}