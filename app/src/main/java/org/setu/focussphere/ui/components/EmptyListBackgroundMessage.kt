package org.setu.focussphere.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun EmptyTaskListMessage(
    @StringRes stringResHeaderId: Int,
    @StringRes stringResMessageId: Int
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(20.dp),
            text = stringResource(id = stringResHeaderId),
                style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = stringResource(id = stringResMessageId),
            style = MaterialTheme.typography.headlineLarge,
            color = Color.LightGray)
    }
}