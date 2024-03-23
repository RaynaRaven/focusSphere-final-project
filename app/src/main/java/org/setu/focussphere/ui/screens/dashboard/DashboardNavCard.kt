package org.setu.focussphere.ui.screens.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.setu.focussphere.R
import org.setu.focussphere.ui.theme.Shapes
import timber.log.Timber.Forest.i

@Composable
fun DashboardNavCard(
    shape: CornerBasedShape = Shapes.small,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit,
    modifier: Modifier= Modifier
) {
    Card (
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = modifier
            .aspectRatio(0.75f)
            .clickable {
                i("DashboardNavCard clicked")
            },
        onClick = onClick
    ) {
        content()
    }
}

@Composable
fun DashboardNavCardContent(
    title: String,
    subTitle: String,
    icon: ImageVector
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .padding(bottom = 4.dp)
        )
        Text(
            text = subTitle,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
        )
        Icon(
            imageVector = icon,
            contentDescription = "image icon",
            modifier = Modifier
                .fillMaxHeight(.8f)
                .size(80.dp),
            tint = Color.DarkGray
        )
    }
}

@Preview
@Composable
fun PreviewDashboardNavCard() {
    DashboardNavCard(
        content = { DashboardNavCardContent(
            title = stringResource(
                R.string.dashboard_nav_card_title_tasks),
            subTitle = stringResource(
                R.string.dashboard_nav_card_subtitle_tasks),
            icon = Icons.AutoMirrored.Outlined.List)
        }
    )
}