package org.setu.focussphere.ui.screens.task.add_edit_task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.setu.focussphere.data.entities.Task
import org.setu.focussphere.data.repository.TaskRepository
import org.setu.focussphere.util.UiEvent
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val repository: TaskRepository,

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

    var category by mutableStateOf("")
        private set

    var estimatedDuration by mutableStateOf("")
        private set

    var priorityLevel by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


/* init reads taskId, and if not default value, means we clicked on an existing task
to edit, and data will be retrieved from repository and loaded into UI fields where it
can be edited/updated.
* */

    init {
        val taskId = savedStateHandle.get<Long>("taskId")!!
        if (taskId != -1L) {
            viewModelScope.launch {
                repository.getTaskById(taskId)?.let { task ->
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
                repository.insertTask(
                    Task(
                        id = task?.id, // update existing if we have old ID
                        title = title,
                        description = description,
//                        category = category,
//                        priorityLevel = priorityLevel,
//                        estimatedDuration = estimatedDuration.toString()
                    )
                )
                    Timber.tag("DatabaseDebug").d("Inserted Task: %s", task)
                sendUiEvent(UiEvent.PopBackStack)
                }
            }
            else -> { Unit }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}