package org.setu.focussphere.ui.screens.routine.routinesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.setu.focussphere.data.entities.Routine
import org.setu.focussphere.data.repository.RoutineRepository
import org.setu.focussphere.util.Routes
import org.setu.focussphere.util.UiEvent
import timber.log.Timber.Forest.i
import javax.inject.Inject

@HiltViewModel
class RoutinesViewModel @Inject constructor(
    private val repository: RoutineRepository
) : ViewModel() {

    val routines = repository.getRoutinesWithTasks()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedRoutine: Routine?  = null

    fun onEvent(event: RoutinesEvent) {
        i(event.toString())
        when (event) {
            is RoutinesEvent.OnRoutineClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_ROUTINE + "?routineId=${event.routine.id}"))
            }

            is RoutinesEvent.OnAddRoutineClick -> {
                i(Routes.ADD_EDIT_ROUTINE)
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_ROUTINE))
            }

            is RoutinesEvent.OnDeleteRoutineClick -> {
                viewModelScope.launch {
                    deletedRoutine = event.routine
                    repository.deleteRoutine(event.routine)
                    sendUiEvent(UiEvent.ShowSnackbar( message = "Routine deleted",
                        action = "Undo"))
                }
            }

            is RoutinesEvent.OnUndoDeleteClick -> {
                deletedRoutine?.let {
                    viewModelScope.launch {
                        repository.insertRoutine(it)
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