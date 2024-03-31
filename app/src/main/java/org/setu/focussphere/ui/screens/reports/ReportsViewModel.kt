package org.setu.focussphere.ui.screens.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.setu.focussphere.data.repository.TaskCompletionRepository
import org.setu.focussphere.ui.screens.taskTracker.TaskTrackerEvent
import org.setu.focussphere.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val repository: TaskCompletionRepository
) : ViewModel() {



    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: TaskTrackerEvent) {}

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }


}
