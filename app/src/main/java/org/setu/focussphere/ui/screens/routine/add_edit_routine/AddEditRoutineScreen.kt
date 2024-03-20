package org.setu.focussphere.ui.screens.routine.add_edit_routine

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import org.setu.focussphere.R
import org.setu.focussphere.util.UiEvent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditRoutineScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditRoutineViewModel = hiltViewModel()
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowSnackbar -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.action
                        )
                    }
                }
                else -> Unit
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditRoutineEvent.OnSaveRoutineClicked)
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save"
                )
            }
        }
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
        )
        {
            //TODO UI styling for textFields
            Text(text = stringResource(R.string.add_edit_routine_title_label),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.title,
                placeholder = { Text( text = stringResource(R.string.add_edit_routine_title_hint)) },
                onValueChange = {
                    viewModel.onEvent(AddEditRoutineEvent.OnTitleChanged(it))
                },
                modifier = Modifier.fillMaxWidth()
            )
            }
        }
    }