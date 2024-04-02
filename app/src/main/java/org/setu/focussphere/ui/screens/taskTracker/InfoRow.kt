package org.setu.focussphere.ui.screens.taskTracker

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun InfoRow(
    routineLabel: String,
    totalDuration: String
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = "$routineLabel",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            textAlign = TextAlign.End,
            text = "Est Duration: $totalDuration",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InfoRowPreview() {
    InfoRow("Morning", "45m")
}