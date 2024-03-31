package org.setu.focussphere.ui.screens.reports
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.setu.focussphere.R
import org.setu.focussphere.util.UiEvent
import timber.log.Timber.Forest.i

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ReportsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier

) {

    val scrollState = rememberScrollState()
    val completionStats by viewModel.completionStats.collectAsState(initial = emptyList())
    val categories by viewModel.categories.collectAsState(initial = emptyList())
    val tasks by viewModel.tasks.collectAsState(initial = emptyList())
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("") }

    i("work work $completionStats")

    Column (modifier = Modifier
        .verticalScroll(scrollState)
        .padding(12.dp)) {

            Text(
                text = stringResource(id = R.string.reports_screen_title),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(8.dp)
            )

            Text(
            text = stringResource(id = R.string.reports_screen_subhead_accuracyScore),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            )  {
                OutlinedTextField(
                    label = { Text(text = stringResource(R.string.add_edit_task_textfield_label_category)) },
                    modifier = Modifier.menuAnchor(),
                    readOnly = true,
                    placeholder = { Text(text = stringResource(R.string.add_edit_task_category_hint)) },
                    value = selectedCategory,
                    onValueChange = { selectedCategory = it },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    }
                )
                ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.categoryName) },
                            onClick = {
                                selectedCategory = category.categoryName
                                expanded = false
                                viewModel.onEvent(ReportsEvent.OnDropdownCategorySelected(category.categoryId))
                            })
                    }
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Completion Stats:", fontWeight = FontWeight.Bold)
            completionStats.forEach { stat ->
                if (stat != null) {
                    Text(text = "Category: ${stat.category}, Day of Week: ${stat.dayOfWeek}, Num Completions: ${stat.numCompletions}")
                }
            }
        }



    }
}