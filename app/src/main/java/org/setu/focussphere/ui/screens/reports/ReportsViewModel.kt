package org.setu.focussphere.ui.screens.reports

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.setu.focussphere.data.entities.CompletionStats
import org.setu.focussphere.data.entities.TaskCompletion
import org.setu.focussphere.data.repository.CategoryRepository
import org.setu.focussphere.data.repository.TaskCompletionRepository
import org.setu.focussphere.data.repository.TaskRepository
import org.setu.focussphere.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val taskCompletionRepository: TaskCompletionRepository,
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    val tasks = taskRepository.getTasks()
    val categories = categoryRepository.getAllCategories()

    private var _categoryCompletionStats = MutableStateFlow<List<CompletionStats>>(emptyList())
    val categoryCompletionStats: Flow<List<CompletionStats>> = _categoryCompletionStats.asStateFlow()

    private var _taskCompletions = MutableStateFlow<List<TaskCompletion>>(emptyList())
    val taskCompletions: Flow<List<TaskCompletion>> = _taskCompletions.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    var categoryId by mutableStateOf(0L)
        private set
    var taskId by mutableStateOf(0L)
        private set

    fun onEvent(event: ReportsEvent) {
        when (event) {
            is ReportsEvent.OnDropdownTaskSelected -> {
                viewModelScope.launch {
                    val taskCompletionsFlow = taskCompletionRepository.getLastTenTaskCompletionsForATaskId(event.taskId)
                    taskCompletionsFlow.collect() { taskCompletionsList ->
                        _taskCompletions.value = taskCompletionsList
                    }
                }

            }
            is ReportsEvent.OnDropdownCategorySelected -> {
                viewModelScope.launch {
                    val completionsFlow = taskCompletionRepository.getTaskCompletionsForCategoryOverPrevSevenDays(event.categoryId)
                    completionsFlow.collect() { completionsList ->
                        _categoryCompletionStats.value = completionsList
                    }
                }
            }
            else -> Unit
        }
    }

    fun updateCategory(newCategoryId: Long) {
        this.categoryId = newCategoryId
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}
