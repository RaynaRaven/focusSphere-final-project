package org.setu.focussphere.ui.screens.task.tasksList

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.setu.focussphere.R
import org.setu.focussphere.data.entities.Task
import org.setu.focussphere.ui.theme.Shapes
import org.setu.focussphere.util.Formatters
import java.time.Duration
import java.time.LocalDateTime
import kotlin.math.roundToInt

@Composable
fun ExpandableTaskCard(
    task: Task,
    accuracy: Float,
    onEvent: (TasksEvent) -> Unit,
    expanded: Boolean = false,
    shape: CornerBasedShape = Shapes.medium,
    modifier: Modifier,
    formatter: Formatters
) {
    var expandedState by rememberSaveable { mutableStateOf(expanded) }
    Card(
        modifier = modifier
            .clickable {
            }
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearEasing
                )
            ),
        shape = shape,
        onClick = {
            onEvent(TasksEvent.OnTaskClick(task))
        },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
    ) {
        TaskCardContent(
            task,
            accuracy,
            onEvent = onEvent,
            task.title,
            formatter.formatDateTimeToDayMonthYear(task.createdDateTime),
            task.description,
            formatter.formatDuration(task.estimatedDuration),
            expanded
        )
    }
}

@Composable
private fun TaskCardContent(
    task: Task,
    accuracy: Float,
    onEvent: (TasksEvent) -> Unit,
    title: String,
    dateCreated: String,
    description: String,
    estimatedDuration: String,
    expanded: Boolean,
    padding: Dp = 8.dp
) {
    var expandedState by rememberSaveable { mutableStateOf(expanded) }
    val rotationState by animateFloatAsState(targetValue = if (expandedState) 180f else 0f,
        label = "label"
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
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier
                        .weight(8f),
                    text = title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    modifier = Modifier
                        .alpha(0.5f)
                        .weight(1f),
                    onClick = {
                        onEvent(TasksEvent.OnDeleteTaskClick(task))
                    }) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp),
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Task")
                }

            }
            Row(verticalAlignment = Alignment.Top){
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text =  stringResource(R.string.taskCard_EstimatedTime) + " $estimatedDuration",
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically){
                Text(
                    modifier = Modifier
                        .weight(8f),
                    text = "Created $dateCreated",
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    modifier = Modifier
                        .alpha(0.5f)
                        .weight(1f)
                        .size(24.dp)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Expand task details")
                }
            }
            if (expandedState) {
                if (accuracy > 0) {
                    Row(
                    ){
                        Text(
                            modifier = Modifier.padding(top = 8.dp),
                            text = "Accuracy Score: ",
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            style = MaterialTheme.typography.bodyMedium
                            )
                        Text(
                            modifier = Modifier.padding(top = 8.dp),
                            text = "${((1 - accuracy) * 100).roundToInt()}%",
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (accuracy>1) Color.Red else Color.Green
                        )
                    }
                    }
                }
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = description,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

@Preview(name = "Collapsed Task Card")
@Composable
fun CollapsedTaskCardPreview() {
    val previewTask = Task(
        title = "Fill Dishwasher",
        createdDateTime = LocalDateTime.now(),
        description = "This is a description of the task",
        estimatedDuration = Duration.ofMinutes(200),
        id = 1L
    )
    ExpandableTaskCard(
        task = previewTask,
        accuracy = 0.8f,
        onEvent = {},
        expanded = false,
        modifier = Modifier,
        formatter = Formatters
    )
}

@Preview(name = "Expanded Task Card")
@Composable
fun ExpandedTaskCardPreview() {
    val previewTask = Task(
        title = "Task Title",
        createdDateTime = LocalDateTime.now(),
        description = "This is a description of the task",
        estimatedDuration = Duration.ofMinutes(200),
        id = 1L
    )
    ExpandableTaskCard(
        task = previewTask,
        accuracy = .96f,
        onEvent = {},
        expanded = true,
        modifier = Modifier,
        formatter = Formatters
    )
}
