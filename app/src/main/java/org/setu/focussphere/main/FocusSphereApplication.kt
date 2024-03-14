package org.setu.focussphere.main

import android.app.Application
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dagger.hilt.android.HiltAndroidApp
import org.setu.focussphere.ui.components.BottomBar
import org.setu.focussphere.ui.components.TopBar
import timber.log.Timber
import timber.log.Timber.DebugTree
import timber.log.Timber.Forest.i

@HiltAndroidApp
class FocusSphereApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())
        i("FocusSphere started")
    }
}
@Composable
fun FocusSphereApp(navController: NavHostController) {
    val currentRoute = navController.currentDestination?.route ?: ""
    Scaffold(
        topBar = {
            TopBar(
                screenTitle = "Tasks",
                onUserIconClick = {
                    i("Topbar user icon clicked")
                }
            )
        },
        bottomBar = {
            BottomBar(
                navController = navController,
                currentRoute = currentRoute
            )
        }
    ) { contentPadding ->
        FocusSphereNavHost(navController = navController,
            modifier = Modifier.padding(contentPadding))
    }
}