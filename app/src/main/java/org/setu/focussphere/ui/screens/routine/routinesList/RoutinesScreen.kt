package org.setu.focussphere.ui.screens.routine.routinesList

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.setu.focussphere.R
import org.setu.focussphere.ui.components.EmptyTaskListMessage
import org.setu.focussphere.util.UiEvent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RoutinesScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: RoutinesViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    //retrieve tasks flow as a composed state
    val routines by viewModel.routines.collectAsState(initial = emptyList())
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
                        viewModel.onEvent(RoutinesEvent.OnUndoDeleteClick)
                    }
                }
                else -> Unit
            }
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(RoutinesEvent.OnAddRoutineClick)
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
        if (routines.isEmpty()) {
            EmptyTaskListMessage(
                stringResHeaderId = R.string.routine_screen_headline,
                stringResMessageId = R.string.routine_screen_background_message
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 12.dp, top = 8.dp, bottom = 4.dp),
                    text = stringResource(id = R.string.routine_screen_headline),
                    style = MaterialTheme.typography.headlineMedium,
                )
                Spacer(modifier = Modifier.padding(4.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(routines) { routine ->
                        ExpandableRoutineCard(
                            routine = routine,
                            onEvent = viewModel::onEvent,
                            modifier = Modifier
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                        )
                    }
                }
            }
        }
    }
}