package org.setu.focussphere.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.setu.focussphere.ui.theme.FocusSphereTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FocusSphereTheme {
                val navController = rememberNavController()
                FocusSphereApp(navController = navController)
            }
        }
    }
}
