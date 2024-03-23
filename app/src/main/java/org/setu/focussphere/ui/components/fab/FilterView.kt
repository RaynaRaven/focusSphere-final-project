package org.setu.focussphere.ui.components.fab

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun FilterView(
    items: List<FilterFabMenuItem>,
    modifier: Modifier = Modifier
) {

    var filterFabState by rememberSaveable() {
        mutableStateOf(FilterFabState.COLLAPSED)
    }

    val transitionState = remember {
        MutableTransitionState(filterFabState).apply {
            targetState = FilterFabState.COLLAPSED
        }
    }

    val transition = updateTransition(targetState = transitionState, label = "transition")

    val iconRotationDegree by transition.animateFloat({
        tween(durationMillis = 150, easing = FastOutSlowInEasing)
    }, label = "rotation") {
        if (filterFabState == FilterFabState.EXPANDED) 225f else 0f
    }

    Column(
        modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Bottom)
    ) {
        FilterFabMenu(items = items, visible = filterFabState == FilterFabState.EXPANDED)
        FilterFab(
            state = filterFabState,
            rotation = iconRotationDegree, onClick = { state ->
                filterFabState = state
            })
    }
}

