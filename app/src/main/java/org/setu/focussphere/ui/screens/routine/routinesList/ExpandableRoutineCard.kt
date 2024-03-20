package org.setu.focussphere.ui.screens.routine.routinesList

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.setu.focussphere.R
import org.setu.focussphere.data.entities.RoutineWithTasks
import org.setu.focussphere.data.entities.Task
import org.setu.focussphere.ui.theme.Shapes

@Composable
fun ExpandableRoutineCard(
    routine: RoutineWithTasks,
    onEvent: (RoutinesEvent) -> Unit,
    expanded: Boolean = false,
    shape: CornerBasedShape = Shapes.medium,
    modifier: Modifier
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
            onEvent(RoutinesEvent.OnRoutineClick(routine.routine))
//            /*expandedState = !expandedState*/
        },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
    ) {
        RoutineCardContent(
            routine.routine.title,
            routine.tasks,
            expanded
        )
    }
}


@Composable
private fun RoutineCardContent(
    title: String,
    tasks: List<Task>,
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier
                        .weight(3f),
                    text =  stringResource(R.string.routineCard_taskCount) + " " + tasks.size.toString(),
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.labelMedium
                )
            }
            //TODO: add a delete icon button
            if (expandedState) {
                Column {
                    tasks.forEach { task ->
                        Text(
                            text = task.title,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }

}