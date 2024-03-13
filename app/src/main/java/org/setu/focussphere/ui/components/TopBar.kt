package org.setu.focussphere.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    screenTitle: String,
    onUserIconClick: () -> Unit,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        title = {
            Row {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "App Logo"
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = screenTitle)
            } },
        actions = {
            IconButton(
                onClick = onUserIconClick
            ) {
                Icon(
                    Icons.Default.AccountCircle,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = "User Account"
                )
            }
        }
    )
}