package org.setu.focussphere.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.setu.focussphere.R
import org.setu.focussphere.ui.theme.FocusSphereTheme
import org.setu.focussphere.util.Routes
import timber.log.Timber


@Composable
fun BottomBar(
    navController: NavController,
    currentRoute: String
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home", tint = Color.White) },
            label = { Text(text = stringResource(R.string.bottom_navigation_home), color = Color.White) },
            selected = currentRoute == Routes.HOME,
            onClick = { Timber.i("Home icon clicked")
            /*navController.navigate(Routes.HOME)*/
            navController.navigate(Routes.TASK_LIST)
            }
        )
        NavigationBarItem(
            //TODO: Add custom icon for dashboard
            icon = { Icon(Icons.Filled.DateRange, contentDescription = "Dashboard", tint = Color.White)  },
            label = { Text(text = stringResource(R.string.bottom_navigation_dashboard), color = Color.White) },
            selected = currentRoute == Routes.DASHBOARD,
            onClick = { Timber.i("Dashboard icon clicked")
                navController.navigate(Routes.DASHBOARD) }
        )
        NavigationBarItem(
            //TODO: Add custom icon for focusMode (target or similar)
            icon = { Icon(Icons.Filled.PlayArrow, contentDescription = "Focus", tint = Color.White) },
            label = { Text(text = stringResource(R.string.bottom_navigation_focus), color = Color.White) },
            selected = currentRoute == Routes.FOCUS,
            onClick = { Timber.i("Focus icon clicked")
            /*navController.navigate(Routes.FOCUS)*/ }
        )
    }
}


//@Composable
//fun PreviewNavController() = rememberNavController()


@Composable
@Preview/*(showBackground = true)*/
fun BottomBarPreview() {
    FocusSphereTheme {
        val navController = rememberNavController()
        val currentRoute = Routes.HOME
        BottomBar(
            navController = navController,
            currentRoute = currentRoute
        )
    }
}