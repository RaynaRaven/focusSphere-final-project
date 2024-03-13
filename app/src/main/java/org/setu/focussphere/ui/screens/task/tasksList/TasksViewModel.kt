package org.setu.focussphere.ui.screens.task.tasksList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.setu.focussphere.data.entities.Task
import org.setu.focussphere.data.repository.TaskRepository
import org.setu.focussphere.util.Routes
import org.setu.focussphere.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    val tasks = repository.getTasks()

    //channel is a flow that can be used to send data to the UI
    //e.g. a channel can be used to send a message to the UI to show a snackbar

    //mutable state variable
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    // nullable task variable to store the task that was deleted, to restore if user clicks undo
    private var deletedTask: Task?  = null

    /*
     onEvent function will be called when a task ui event is sent to the viewModel,
     and will handle the event and update the state of the viewModel. Using a when statement
     to handle different types of events keeps the code clean and easy to read and avoids
     using multiple functions to handle different events.
     */
    fun onEvent(event: TasksEvent) {
        when (event) {
            is TasksEvent.OnTaskClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TASK + "?taskId=${event.task.id}"))
            }

            is TasksEvent.OnAddTaskClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TASK))
            }

            is TasksEvent.OnDeleteTaskClick -> {
               viewModelScope.launch {
                   deletedTask = event.task
                   repository.deleteTask(event.task)
                   sendUiEvent(UiEvent.ShowSnackbar( message = "Task deleted",
                         action = "Undo"))
               }
            }

            is TasksEvent.OnUndoDeleteClick -> {
                deletedTask?.let {
                    viewModelScope.launch {
                        repository.insertTask(it)
                    }
                }
            }

            is TasksEvent.OnDoneChange -> {
                viewModelScope.launch {
                    repository.insertTask(
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