package org.setu.focussphere.ui.components.fab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun FilterFabMenuItem(
    menuItem: FilterFabMenuItem,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        //label
        FilterFabMenuLabel(label = menuItem.label)

        //fab
        FilterFabMenuButton(item = menuItem)

    }

}

data class FilterFabMenuItem(
    val label: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)