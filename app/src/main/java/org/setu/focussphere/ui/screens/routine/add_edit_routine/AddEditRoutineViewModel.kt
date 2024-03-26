package org.setu.focussphere.ui.screens.routine.add_edit_routine

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.setu.focussphere.data.entities.Routine
import org.setu.focussphere.data.entities.RoutineTaskCrossRef
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

    val tasks: Flow<List<Task>> = taskRepository.getTasks()

    private val _selectedTasksFlow = MutableStateFlow<List<Long>>(emptyList())
    val selectedTasks: Flow<List<Long>> = _selectedTasksFlow.asStateFlow()

/* init reads routineId, and if not default value, means we clicked on an existing routine
to edit, and data will be retrieved from repository and loaded into UI fields where it
can be edited/updated.
*/
    init {
        val routineId = savedStateHandle.get<Long>("routineId")!!
        if (routineId != 0L) {
            viewModelScope.launch {
                routineRepository.getRoutineById(routineId)?.let { routine ->
                    title = routine.title
                    this@AddEditRoutineViewModel.routine = routine
                    val associatedTaskIds = routineRepository.getTaskIdsForRoutine(routineId).first()
                    _selectedTasksFlow.value = associatedTaskIds
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
                    if (title.isBlank()) {
                    sendUiEvent(UiEvent.ShowSnackbar("Please update all fields"))
                    return@launch
                }
                val routineId = routine?.id ?: 0L
                val insertedId = routineRepository.insertRoutine(
                    Routine(
                        id = routineId,
                        title = title,
                    )
                )
                    //update cross ref table here
                updateRoutineTaskCrossRef(insertedId, selectedTasks)
                Timber.tag("DatabaseDebug").d("Inserted Routine: $insertedId")
//                sendUiEvent(UiEvent.PopBackStack)
                    sendUiEvent(UiEvent.Navigate(Routes.ROUTINES_LIST))
                }
            }
            else -> { Unit }
        }
    }

    private suspend fun updateRoutineTaskCrossRef(routineId: Long, selectedTaskIdsFlow: Flow<List<Long>>) {
        val existingTaskIds = routineRepository.getTaskIdsForRoutine(routineId).first().toSet()
        val selectedTaskIds = selectedTaskIdsFlow.first()

        //delete removed associations
        existingTaskIds.forEach { existingTaskId ->
            if (existingTaskId !in selectedTaskIds) {
                routineRepository.deleteCrossRef(RoutineTaskCrossRef(routineId, existingTaskId))
            }
        }

        //add new associations
        selectedTaskIds.forEach() { selectedTaskId ->
            if (selectedTaskId !in existingTaskIds) {
                routineRepository.insertCrossRef(RoutineTaskCrossRef(routineId, selectedTaskId))
            }

        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    fun toggleTaskSelection(taskId: Long) {
        val updatedList = _selectedTasksFlow.value.toMutableList()
        if (updatedList.contains(taskId)) {
            updatedList.remove(taskId)
        } else {
            updatedList.add(taskId)
        }
        _selectedTasksFlow.value = updatedList
    }

}