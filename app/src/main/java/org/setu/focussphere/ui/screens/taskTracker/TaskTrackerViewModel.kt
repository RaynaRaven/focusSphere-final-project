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
import org.setu.focussphere.data.entities.TaskCompletion
import org.setu.focussphere.data.repository.RoutineRepository
import org.setu.focussphere.data.repository.TaskCompletionRepository
import org.setu.focussphere.data.repository.TaskRepository
import org.setu.focussphere.util.UiEvent
import timber.log.Timber.Forest.i
import java.time.Duration
import java.time.Instant
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TaskTrackerViewModel @Inject constructor(
    private val routineRepository: RoutineRepository,
    private val taskRepository: TaskRepository,
    private val taskCompletionRepository: TaskCompletionRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // State for selected routine
    private val _selectedRoutine = MutableStateFlow<Routine?>(null)
    val selectedRoutine: StateFlow<Routine?> = _selectedRoutine.asStateFlow()

    // State for tasks associated with the selected routine
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    // State for total duration of tasks
    val totalDuration = tasks.map {
        it.fold(Duration.ZERO) { acc, task ->
            acc + (task.estimatedDuration)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, Duration.ZERO)

    //State for task queue, current task and transition between tasks
    val _queuedTasks = MutableStateFlow<List<Task>>(emptyList())
    val currentTask = _queuedTasks.map { tasks ->
        tasks.firstOrNull()
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    val running = MutableStateFlow(false)

    private var taskStartTime: Date? = null

    val routines = routineRepository.getRoutines()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: TaskTrackerEvent) {
        i("Task Tracker Event; $event")
        when (event) {
            is TaskTrackerEvent.OnAddRoutineClick -> {
            }
            is TaskTrackerEvent.OnStartClick -> {
                viewModelScope.launch {
                    taskStartTime = Date()
                    running.value = true
                }

            }
            is TaskTrackerEvent.OnStopClick -> {
                viewModelScope.launch {
                    //show dialog to confirm stop
                    val took = Date().time - taskStartTime!!.time
//                    val took = Duration.between(taskStartTime!!.toInstant(), Date().toInstant())
                    running.value = false

                    // save task completion to database
                    currentTask.value?.let { currentTask ->
                        selectedRoutine.value?.let { currentRoutine ->
                            taskCompletionRepository.insertTaskCompletion(
                                TaskCompletion(
                                    taskId = currentTask.id,
                                    routineId = currentRoutine.id,
                                    completionTime = Instant.now().toEpochMilli(),
                                    duration = took,
                                    accuracy = calculateAccuracy(took, currentTask.estimatedDuration)
                                )
                            )
                        }
                    }
                    i("Task duration: $took")

                    _queuedTasks.value?.let {
                        _queuedTasks.value = _queuedTasks.value.drop(1)
                    }

                    i("Current task: ${_queuedTasks.value.firstOrNull()}")

                }
                //show dialog to confirm stop
                //if yes, cue up the next task, if cancel leave it running
                //get current time on timer
                //log task completion

            }
            is TaskTrackerEvent.OnDoneChange-> {}
            is TaskTrackerEvent.OnRoutineClick -> {
                viewModelScope.launch {
                    _selectedRoutine.value = event.routine
                    i(_selectedRoutine.value.toString())
                    routineRepository.getTaskIdsForRoutine(event.routine.id).collect() {
                        _tasks.value = taskRepository.getTasksByIds(it)
                                           _queuedTasks.value = _tasks.value

                    }
                }
            }
            is TaskTrackerEvent.OnTimerExpired -> {

            }
            else -> Unit
        }
    }

    private fun calculateAccuracy( took: Long, estimatedDuration: Duration): Float {
        val accuracyScore = if (estimatedDuration.toMillis() > 0) {
            took.toFloat()/estimatedDuration.toMillis().toFloat()
        } else {
            0f
        }
        i("Calc accuracy score: $accuracyScore")
        return accuracyScore
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}