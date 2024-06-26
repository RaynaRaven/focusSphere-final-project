package org.setu.focussphere.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.setu.focussphere.ui.screens.dashboard.DashboardScreen
import org.setu.focussphere.ui.screens.home.HomeScreen
import org.setu.focussphere.ui.screens.reports.ReportsScreen
import org.setu.focussphere.ui.screens.routine.add_edit_routine.AddEditRoutineScreen
import org.setu.focussphere.ui.screens.routine.routinesList.RoutinesScreen
import org.setu.focussphere.ui.screens.task.add_edit_task.AddEditTaskScreen
import org.setu.focussphere.ui.screens.task.tasksList.TasksScreen
import org.setu.focussphere.ui.screens.taskTracker.TaskTrackerScreen
import org.setu.focussphere.util.Routes

@Composable
fun FocusSphereNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                onNavigate = {
                    navController.navigate(it.route)
                }
            )
        }
        composable(Routes.DASHBOARD) {
            DashboardScreen(
                onNavigate = {
                    navController.navigate(it.route)
                }
            )
        }
        composable(Routes.TASK_LIST) {
            TasksScreen(
                onNavigate = {
                    navController.navigate(it.route)
                }
            )
        }
        composable(Routes.ROUTINES_LIST) {
            RoutinesScreen(
                onNavigate = {
                    navController.navigate(it.route)
                }
            )
        }
        composable(Routes.TASK_TRACKER) {
            TaskTrackerScreen(
                onNavigate = {
                    navController.navigate(it.route)
                }
            )
        }
        composable(Routes.REPORTS) {
            ReportsScreen(
                onNavigate = {
                    navController.navigate(it.route)
                }
            )
        }
        composable(
            route = Routes.ADD_EDIT_TASK + "?taskId={taskId}",
            arguments = listOf(
                navArgument(name = "taskId") {
                    type = NavType.LongType
                    defaultValue = 0L
                })
        ) {
            backStackEntry ->
            val taskId = backStackEntry.arguments?.getLong("taskId") ?: 0L

            AddEditTaskScreen(
                onPopBackStack = {
                    navController.popBackStack()
                },
                navController = navController,
                taskId = taskId)
        }
        composable(
            route = Routes.ADD_EDIT_ROUTINE + "?routineId={routineId}",
            arguments = listOf(
                navArgument(name = "routineId") {
                    type = NavType.LongType
                    defaultValue = -1
                })
        ) {
            AddEditRoutineScreen(
                onPopBackStack = {
                    navController.popBackStack()
                },
                navController = navController
            )
        }
    }
}