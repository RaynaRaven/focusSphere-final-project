package org.setu.focussphere.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.setu.focussphere.R
import org.setu.focussphere.ui.theme.Shapes
import java.time.Duration


@Composable
fun ExpandableTaskCard(
    title: String,
    dateCreated: String,
    description: String,
    estimatedDuration: Duration,
    expanded: Boolean,
    shape: CornerBasedShape = Shapes.medium,
    padding: Dp = 8.dp
) {
    var expandedState by rememberSaveable { mutableStateOf(expanded) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearEasing
                )
            ),
        shape = shape,
        onClick = {
            expandedState = !expandedState
        },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
    ) {
        TaskCardContent(
            title,
            dateCreated,
            description,
            estimatedDuration,
            expanded
        )
    }
}

@Composable
private fun TaskCardContent(
    title: String,
    dateCreated: String,
    description: String,
    estimatedDuration: Duration,
    expanded: Boolean,
    padding: Dp = 8.dp
) {
    var expandedState by rememberSaveable { mutableStateOf(expanded) }
    val rotationState by animateFloatAsState(targetValue = if (expandedState) 180f else 0f,
        label = "wat"
    )
    Row(
        modifier = Modifier
            .padding(padding)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(padding)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier
                        .weight(3f),
                    text = title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier
                        .weight(3f),
                    text =  stringResource(R.string.taskCard_EstimatedTime) + estimatedDuration,
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.labelMedium
                )
                IconButton(
                    modifier = Modifier
                        .alpha(0.5f)
                        .weight(1f)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Expand task details")
                }
            }
            Text(
                text = "Created $dateCreated",
                style = MaterialTheme.typography.labelMedium
            )
            if (expandedState) {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = description,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

}

@Preview(name = "Collapsed Task Card")
@Composable
fun CollapsedTaskCardPreview() {
    ExpandableTaskCard(
        title = "Task Title",
        dateCreated = "2024-02-01",
        description = "This is a description of the task",
        estimatedDuration = Duration.ofHours(1),
        expanded = false
    )
}

@Preview(name = "Expanded Task Card")
@Composable
fun ExpandedTaskCardPreview() {
    ExpandableTaskCard(
        title = "Task Title",
        dateCreated = "2024-02-01",
        description = "This is a description of the task. This is a description of the task. This is a description of the task",
        estimatedDuration = Duration.ofHours(1),
        expanded = true
    )
}
