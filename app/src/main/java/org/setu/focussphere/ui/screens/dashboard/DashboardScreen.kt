package org.setu.focussphere.ui.screens.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.setu.focussphere.R
import org.setu.focussphere.ui.components.fab.FilterFabMenuItem
import org.setu.focussphere.ui.components.fab.FilterView
import org.setu.focussphere.ui.screens.routine.routinesList.RoutinesEvent
import org.setu.focussphere.ui.screens.routine.routinesList.RoutinesViewModel
import org.setu.focussphere.ui.screens.task.tasksList.TasksEvent
import org.setu.focussphere.ui.screens.task.tasksList.TasksViewModel
import org.setu.focussphere.util.Routes
import org.setu.focussphere.util.UiEvent
import timber.log.Timber.Forest.i

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    tasksViewModel: TasksViewModel = hiltViewModel(),
    routinesViewModel: RoutinesViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    LaunchedEffect(key1 = true) {
        tasksViewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    LaunchedEffect(key1 = true) {
        routinesViewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    val fabMenuItems = listOf<FilterFabMenuItem>(
        FilterFabMenuItem(
            label = stringResource(id = R.string.fab_add_task_label),
            icon = Icons.Default.Add,
            onClick = {
                i("DashboardScreen: Add Task Clicked")
                tasksViewModel.onEvent(TasksEvent.OnAddTaskClick)
            }
        ),
        FilterFabMenuItem(
            label = stringResource(id = R.string.fab_add_routine_label),
            icon = Icons.Default.Add,
            onClick = {
                i("DashboardScreen: Add Routine Clicked")
                routinesViewModel.onEvent(RoutinesEvent.OnAddRoutineClick)
            }
        )
    )

    Scaffold(
        floatingActionButton = {
            FilterView(
                items = fabMenuItems
            )
        }
    ) {
        //composable of 4 clickable cards in a 2x2 grid with a header
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .height(36.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = stringResource(id = R.string.dashboard_screen_title),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
            }
            Spacer(modifier = Modifier.height(36.dp))
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom

            ) {
                DashboardNavCard(
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(UiEvent.Navigate(Routes.TASK_LIST))},
                    content = {
                        DashboardNavCardContent(
                            title = stringResource(id = R.string.dashboard_nav_card_title_tasks),
                            subTitle = stringResource(id = R.string.dashboard_nav_card_subtitle_tasks),
                            icon = Icons.AutoMirrored.Outlined.List
                        )
                    }
                )
                Spacer(modifier = Modifier.width(16.dp))
                DashboardNavCard(
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(UiEvent.Navigate(Routes.ROUTINES_LIST))},
                    content = {
                        DashboardNavCardContent(
                            title = stringResource(id = R.string.dashboard_nav_card_title_routines),
                            subTitle = stringResource(id = R.string.dashboard_nav_card_subtitle_routines),
                            icon = Icons.Outlined.Create
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.height(48.dp))
            Row(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()
            ) {
                DashboardNavCard(
                    modifier = Modifier.weight(1f),
                    //TODO: Enable route when TaskTracker is implemented
                    onClick = {/* onNavigate(UiEvent.Navigate(Routes.TASK_TRACKER))*/},
                    content = {
                        DashboardNavCardContent(
                            title = stringResource(id = R.string.dashboard_nav_card_title_taskTracker),
                            subTitle = stringResource(id = R.string.dashboard_nav_card_subtitle_taskTracker),
                            icon = Icons.Outlined.Info
                        )
                    }
                )
                Spacer(modifier = Modifier.padding(8.dp))
                DashboardNavCard(
                    modifier = Modifier.weight(1f),
                    //TODO: Enable route when Reports is implemented
                    onClick = {/* onNavigate(UiEvent.Navigate(Routes.REPORTS))*/},
                    content = {
                        DashboardNavCardContent(
                            title = stringResource(id = R.string.dashboard_nav_card_title_reports),
                            subTitle = stringResource(id = R.string.dashboard_nav_card_subtitle_reports),
                            icon = Icons.Outlined.Info
                        )
                    }
                )
            }

        }

    }
}

@Composable
@Preview(showBackground = true)
fun DashboardScreenPreview() {
    DashboardScreen(onNavigate = {})
}

