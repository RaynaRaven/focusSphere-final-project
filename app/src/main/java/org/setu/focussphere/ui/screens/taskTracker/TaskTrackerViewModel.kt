package org.setu.focussphere.ui.screens.taskTracker

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.setu.focussphere.data.entities.Routine
import org.setu.focussphere.data.entities.Task
import org.setu.focussphere.data.repository.RoutineRepository
import org.setu.focussphere.data.repository.TaskRepository
import org.setu.focussphere.util.UiEvent
import timber.log.Timber.Forest.i
import java.time.Duration
import javax.inject.Inject

@HiltViewModel
class TaskTrackerViewModel @Inject constructor(
    private val routineRepository: RoutineRepository,
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // State for selected routine
    private val _selectedRoutine = MutableStateFlow<Routine?>(null)
    val selectedRoutine: StateFlow<Routine?> = _selectedRoutine.asStateFlow()

    // State fortasks associated with the selected routine
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    val totalDuration = tasks.map {
        it.fold(Duration.ZERO) { acc, task ->
            acc + (task.estimatedDuration)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, Duration.ZERO)

    val routines = routineRepository.getRoutines()
    val routinesWithTasks = routineRepository.getRoutinesWithTasks()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: TaskTrackerEvent) {
        i("Task Tracker Event; $event")
        when (event) {
            is TaskTrackerEvent.OnAddRoutineClick -> {
            }
            is TaskTrackerEvent.OnStartClick -> {

            }
            is TaskTrackerEvent.OnStopClick -> {}
            is TaskTrackerEvent.OnDoneChange-> {}
            is TaskTrackerEvent.OnRoutineClick -> {
                viewModelScope.launch {
                    _selectedRoutine.value = event.routine
                    i(_selectedRoutine.value.toString())
                    routineRepository.getTaskIdsForRoutine(event.routine.id).collect() {
                        _tasks.value = taskRepository.getTasksByIds(it)
                    }
                }


            }
            else -> Unit
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}