package org.setu.focussphere.ui.screens.reports

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.setu.focussphere.R
import org.setu.focussphere.util.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ReportsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier

) {

    val scrollState = rememberScrollState()
    val completionStats by viewModel.categoryCompletionStats.collectAsState(initial = emptyList())
    val taskCompletions by viewModel.taskCompletions.collectAsState(initial = emptyList())
    val categories by viewModel.categories.collectAsState(initial = emptyList())
    val tasks by viewModel.tasks.collectAsState(initial = emptyList())
    var selectedTask by remember { mutableStateOf("") }
    var taskDropdownExpanded by remember { mutableStateOf(false) }
    var categoryDropdownExpanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(12.dp)
    ) {
        Text(
            text = stringResource(id = R.string.reports_screen_subhead_accuracyScore),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(8.dp)
        )
        Text(
            text = "Completion Scores",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(8.dp)
        )
        ExposedDropdownMenuBox(
            expanded = taskDropdownExpanded,
            onExpandedChange = { taskDropdownExpanded = !taskDropdownExpanded },
            modifier = Modifier.padding(start = 20.dp, end = 20.dp)
        ) {
            OutlinedTextField(
                label = { Text(text = stringResource(R.string.reports_screen_dropdownLabel_selectTask)) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .padding(12.dp),
                readOnly = true,
                placeholder = { Text(text = stringResource(R.string.reports_screen_dropdownLabel_selectTask)) },
                value = selectedTask,
                onValueChange = { selectedTask = it },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = taskDropdownExpanded)
                }
            )
            ExposedDropdownMenu(
                expanded = taskDropdownExpanded,
                onDismissRequest = { taskDropdownExpanded = false },
            ) {
                tasks.forEach { task ->
                    DropdownMenuItem(
                        text = { Text(task.title) },
                        onClick = {
                            selectedTask = task.title
                            taskDropdownExpanded = false
                            viewModel.onEvent(ReportsEvent.OnDropdownTaskSelected(task.id))
                        })
                }
            }
        }
        if (selectedTask.isNotBlank())
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                TasksPlotChart(
                    completionStats = taskCompletions,
                    modifier = modifier.padding(20.dp)
                )
            }

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Completions per category",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(8.dp)
        )
        ExposedDropdownMenuBox(
            expanded = categoryDropdownExpanded,
            onExpandedChange = { categoryDropdownExpanded = !categoryDropdownExpanded },
            modifier = Modifier.padding(start = 20.dp, end = 20.dp)
        ) {
            OutlinedTextField(
                label = { Text(text = stringResource(R.string.add_edit_task_category_hint)) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxSize(),
                readOnly = true,
                placeholder = { Text(text = stringResource(R.string.add_edit_task_category_hint)) },
                value = selectedCategory,
                onValueChange = { selectedCategory = it },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryDropdownExpanded)
                }
            )
            ExposedDropdownMenu(
                expanded = categoryDropdownExpanded,
                onDismissRequest = { categoryDropdownExpanded = false },
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.categoryName) },
                        onClick = {
                            selectedCategory = category.categoryName
                            categoryDropdownExpanded = false
                            viewModel.onEvent(ReportsEvent.OnDropdownCategorySelected(category.categoryId))
                        })
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        if (selectedCategory.isNotBlank()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                CompletionStatsHistogram(
                    completionStats = completionStats,
                    modifier = modifier.padding(20.dp)
                )
            }
        }
    }
}