package org.setu.focussphere.ui.screens.taskTracker

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TimerControlButtons(
    onEvent: (TaskTrackerEvent) -> Unit,
) {
    Row() {
        Spacer(modifier = Modifier
            .padding(16.dp))
        Button(

            onClick = { onEvent(TaskTrackerEvent.OnStartClick)},
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
        ) {
            Text(text = "Start")
        }
        Spacer(modifier = Modifier
            .padding(8.dp))
        Button(
            onClick = { onEvent(TaskTrackerEvent.OnStopClick) },
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
        ) {
            Text(text = "Stop")
        }
        Spacer(modifier = Modifier
            .padding(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun TimerControlButtonsPreview() {
    TimerControlButtons(onEvent = {})
}