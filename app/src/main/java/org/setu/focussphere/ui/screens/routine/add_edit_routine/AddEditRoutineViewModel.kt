package org.setu.focussphere.ui.screens.routine.add_edit_routine

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.setu.focussphere.data.entities.Routine
import org.setu.focussphere.data.entities.Task
import org.setu.focussphere.data.repository.RoutineRepository
import org.setu.focussphere.data.repository.TaskRepository
import org.setu.focussphere.util.Routes
import org.setu.focussphere.util.UiEvent
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddEditRoutineViewModel @Inject constructor(
    private val routineRepository: RoutineRepository,
    private val taskRepository: TaskRepository,
    //SavedStateHandle is a key-value store that can be used to save UI state
    // and retrieve data. Can contain navigation arguments, such as routineId
    savedStateHandle: SavedStateHandle
) : ViewModel() {

/*    different states of the add/edit routine screen
    by keyword delegates property access to another object
    private set means that props can only be set within class
    i.e restricted access to setter from outside class*/
    var routine by mutableStateOf<Routine?>(null)
        private set

    var title by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val tasks: LiveData<List<Task>> = taskRepository.getTasks().asLiveData()

    private val _selectedTasks = mutableStateListOf<Long>()
    val selectedTasks: List<Long> get() = _selectedTasks

/* init reads routineId, and if not default value, means we clicked on an existing routine
to edit, and data will be retrieved from repository and loaded into UI fields where it
can be edited/updated.
*/
    init {
        val routineId = savedStateHandle.get<Long>("routineId")!!
        if (routineId != -1L) {
            viewModelScope.launch {
                routineRepository.getRoutineById(routineId)?.let { routine ->
                    title = routine.title
                    //TODO enable when category entity impl
                    this@AddEditRoutineViewModel.routine = routine
                }
            }
        }
    }

    fun onEvent(event: AddEditRoutineEvent) {
        when (event) {
            is AddEditRoutineEvent.OnTitleChanged -> {
                title = event.title
            }
            is AddEditRoutineEvent.OnSaveRoutineClicked -> {
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
                routineRepository.insertRoutine(
                    Routine(
                        id = routine?.id ?: 0, // update existing if we have old ID
                        title = title,
                    )
                )
                    Timber.tag("DatabaseDebug").d("Inserted Routine: %s", routine)
//                sendUiEvent(UiEvent.PopBackStack)
                    sendUiEvent(UiEvent.Navigate(Routes.ROUTINES_LIST))
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

    fun toggleTaskSelection(taskId: Long) {
        if (_selectedTasks.contains(taskId)) {
            _selectedTasks.remove(taskId)
        } else
            _selectedTasks.add(taskId)
    }

}