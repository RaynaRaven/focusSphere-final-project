package org.setu.focussphere.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.setu.focussphere.ui.screens.task.add_edit_task.AddEditTaskScreen
import org.setu.focussphere.ui.screens.task.tasksList.TasksScreen
import org.setu.focussphere.util.Routes

@Composable
fun FocusSphereNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.TASK_LIST,
                modifier = Modifier
    ) {
        composable(Routes.TASK_LIST) {
            TasksScreen(
                onNavigate = {
                    navController.navigate(it.route)
                }
            )
        }
        composable(
            route = Routes.ADD_EDIT_TASK + "?TaskId={taskId}",
            arguments = listOf(
                navArgument(name = "taskId") {
                    type = NavType.LongType
                    defaultValue = -1
                })
        ) {
            AddEditTaskScreen(
                onPopBackStack = {
                    navController.popBackStack()
                })
        }
    }
}