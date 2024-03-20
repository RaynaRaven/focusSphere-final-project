package org.setu.focussphere.ui.components.fab

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

// composable for each menu item from expanded FAB
@Composable
fun FilterFabMenuButton(
    item: FilterFabMenuItem,
//    onClick: (FilterFabMenuItem) -> Unit,
    modifier: Modifier = Modifier
) {

    FloatingActionButton(
        modifier = modifier,
        onClick = item.onClick,
/*        backgroundColor = colorResource(
            id = R.color.primary_color
        )*/
    ) {
        Icon(
/*            painter = painterResource(item.icon), contentDescription = null, tint = colorResource(
                id = R.color.white
            )*/
            item.icon, contentDescription = item.label, tint = Color.White)
    }
}