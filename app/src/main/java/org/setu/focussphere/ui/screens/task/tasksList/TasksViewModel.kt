package org.setu.focussphere.ui.screens.task.tasksList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.setu.focussphere.data.entities.Task
import org.setu.focussphere.data.entities.TaskWithAccuracy
import org.setu.focussphere.data.repository.TaskCompletionRepository
import org.setu.focussphere.data.repository.TaskRepository
import org.setu.focussphere.util.Routes
import org.setu.focussphere.util.UiEvent
import timber.log.Timber.Forest.i
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskCompletionRepository: TaskCompletionRepository
) : ViewModel() {

    val tasks = taskRepository.getTasks()


    private var _tasksWithAccuracy = MutableStateFlow<List<TaskWithAccuracy>>(emptyList())
    val tasksWithAccuracy: Flow<List<TaskWithAccuracy>> = _tasksWithAccuracy.asStateFlow()

    init {
        viewModelScope.launch {
            tasks.collect { taskList ->
                val taskWithAccuracyList = mutableListOf<TaskWithAccuracy>()
                taskList.forEach { task ->
                    val completion = taskCompletionRepository.getMostRecentCompletionByTask(task.id)
                    taskWithAccuracyList.add(
                        TaskWithAccuracy(
                            task,
                            accuracy = completion?.accuracy ?: 0f
                        )
                    )
                }
                _tasksWithAccuracy.value = taskWithAccuracyList
            }
        }
    }

        //channel is a flow that can be used to send data to the UI
        //e.g. a channel can be used to send a message to the UI to show a snackbar

        //mutable state variable
        private val _uiEvent = Channel<UiEvent>()
        val uiEvent = _uiEvent.receiveAsFlow()

        // nullable task variable to store the task that was deleted, to restore if user clicks undo
        private var deletedTask: Task? = null

        /*
     onEvent function will be called when a task ui event is sent to the viewModel,
     and will handle the event and update the state of the viewModel. Using a when statement
     to handle different types of events keeps the code clean and easy to read and avoids
     using multiple functions to handle different events.
     */
        fun onEvent(event: TasksEvent) {
            when (event) {
                is TasksEvent.OnTaskClick -> {
                    i(event.task.id.toString())
                    sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TASK + "?taskId=${event.task.id}"))
                }

                is TasksEvent.OnAddTaskClick -> {
                    i("add task event received after click")
                    sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TASK))
                }

                is TasksEvent.OnDeleteTaskClick -> {
                    viewModelScope.launch {
                        deletedTask = event.task
                        taskRepository.deleteTask(event.task)
                        sendUiEvent(
                            UiEvent.ShowSnackbar(
                                message = "Task deleted",
                                action = "Undo"
                            )
                        )
                    }
                }

                is TasksEvent.OnUndoDeleteClick -> {
                    deletedTask?.let {
                        viewModelScope.launch {
                            taskRepository.insertTask(it)
                        }
                    }
                }

                is TasksEvent.OnDoneChange -> {
                    viewModelScope.launch {
                        taskRepository.insertTask(
                            event.task.copy(
                                isDone = event.isDone
                            )
                        )
                    }
                }
            }
        }

        private fun sendUiEvent(event: UiEvent) {
            viewModelScope.launch {
                _uiEvent.send(event)
            }
        }

}
