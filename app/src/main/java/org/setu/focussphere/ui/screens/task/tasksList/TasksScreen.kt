package org.setu.focussphere.ui.screens.task.tasksList


import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.setu.focussphere.util.UiEvent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TasksScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TasksViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    //retrieve tasks flow as a composed state
    val tasks by viewModel.tasks.collectAsState(initial = emptyList())
    val snackbarHostState = remember { SnackbarHostState() }

    //collect ui events, executes
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowSnackbar -> {
                    val result = snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(TasksEvent.OnUndoDeleteClick)
                    }
                }
                else -> Unit
            }
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(TasksEvent.OnAddTaskClick)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        LazyColumn (
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = tasks,
                key = { task -> task.id!! }
            ) { task ->
                ExpandableTaskCard(
                    task = task,
                    onEvent = viewModel::onEvent,
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                        .clickable {
                            viewModel.onEvent(TasksEvent.OnTaskClick(task))
                        }
                )
            }
        }
    }
}