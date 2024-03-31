package org.setu.focussphere.ui.screens.reports
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.setu.focussphere.R
import org.setu.focussphere.util.UiEvent

@Composable
fun ReportsScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ReportsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier

) {

    val scrollState = rememberScrollState()

    Column (modifier = Modifier
        .verticalScroll(scrollState)
        .padding(12.dp)) {
            Text(
                text = stringResource(id = R.string.reports_screen_title),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.Start).padding(8.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))








    }