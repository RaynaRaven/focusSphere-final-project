package org.setu.focussphere.ui.screens.reports

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import org.setu.focussphere.util.UiEvent

@Composable
fun ReportsScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ReportsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier

) {
    Column {
        Row(){
            Text("Reports")
        }
    }



}
