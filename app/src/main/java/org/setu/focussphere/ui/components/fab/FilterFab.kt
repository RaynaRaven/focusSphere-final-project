package org.setu.focussphere.ui.components.fab

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FilterFab(
    state: FilterFabState,
    rotation:Float,
    onClick: (FilterFabState) -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        modifier = modifier
            .rotate(rotation),
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp),
        onClick = {
            onClick(
                if (state == FilterFabState.EXPANDED) {
                    FilterFabState.COLLAPSED
                } else {
                    FilterFabState.EXPANDED
                }
            )
        },
/*        backgroundColor = colorResource(
            R.color.primary_color
        ),*/
        shape = CircleShape
    ) {
        Icon(
            imageVector = Icons.Default.Add,
//            painter = painterResource(R.drawable.fab_add),
            contentDescription = null,
            tint = Color.White
        )
    }

}