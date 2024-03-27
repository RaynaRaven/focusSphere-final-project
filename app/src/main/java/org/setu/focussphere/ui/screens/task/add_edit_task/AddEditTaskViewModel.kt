package org.setu.focussphere.ui.screens.task.add_edit_task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.setu.focussphere.data.entities.Category
import org.setu.focussphere.data.entities.Task
import org.setu.focussphere.data.repository.CategoryRepository
import org.setu.focussphere.data.repository.TaskRepository
import org.setu.focussphere.util.Routes
import org.setu.focussphere.util.UiEvent
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository,

    //SavedStateHandle is a key-value store that can be used to save UI state
    // and retrieve data. Can contain navigation arguments, such as taskId
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    //different states of the add/edit task screen
    //by keyword delegates property access to another object
    //private set means that props can only be set within class
    //i.e restricted access to setter from outside class

    var task by mutableStateOf<Task?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var categoryId by mutableStateOf(0L)
        private set

    var estimatedDuration by mutableStateOf("")
        private set

    var priorityLevel by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val categories: Flow<List<Category>> = categoryRepository.getAllCategories()/*.catch { emit(
        emptyList()) }.asLiveData().asFlow()*/


/* init reads taskId, and if not default value, means we clicked on an existing task
to edit, and data will be retrieved from repository and loaded into UI fields where it
can be edited/updated.
* */

    init {
        val taskId = savedStateHandle.get<Long>("taskId")!!
        if (taskId != 0L) {
            viewModelScope.launch {
                taskRepository.getTaskById(taskId)?.let { task ->
                    title = task.title
                    description = task.description
                    //TODO enable when category entity impl
//                    category = task.category
//                    priorityLevel = task.priorityLevel,
                    estimatedDuration = task.estimatedDuration.toString()
                    this@AddEditTaskViewModel.task = task
                }
            }
        }
    }

    fun onEvent(event: AddEditTaskEvent) {
        when (event) {
            is AddEditTaskEvent.OnTitleChanged -> {
                title = event.title
            }
            is AddEditTaskEvent.OnDescriptionChanged -> {
                description = event.description
            }
            is AddEditTaskEvent.OnEstimatedDurationChanged -> {
                estimatedDuration = event.estimatedDuration
            }
            is AddEditTaskEvent.OnPriorityLevelChanged -> {
                priorityLevel = event.priorityLevel
            }
            is AddEditTaskEvent.OnSaveTaskClicked -> {
                viewModelScope.launch {
//                    if (title.isBlank() || estimatedDuration.isBlank())
                    if (title.isBlank())
                    {
                    sendUiEvent(
                        UiEvent.ShowSnackbar(
                            "Please update all fields"
                        ))
                    return@launch
                }
                taskRepository.insertTask(
                    Task(
                        id = task?.id ?: 0, // update existing if we have old ID
                        title = title,
                        description = description,
//                        category = category,
//                        priorityLevel = priorityLevel,
//                        estimatedDuration = estimatedDuration.toString()
                    )
                )
                    Timber.tag("DatabaseDebug").d("Inserted Task: %s", task)
//                sendUiEvent(UiEvent.PopBackStack)
                sendUiEvent(UiEvent.Navigate(Routes.TASK_LIST))
                }
            }
            else -> { Unit }
        }
    }

    fun createCategory(categoryName: String) {
        viewModelScope.launch {
            val newCategoryId = categoryRepository.insertCategory(Category(categoryName = categoryName))
            categoryId = newCategoryId
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