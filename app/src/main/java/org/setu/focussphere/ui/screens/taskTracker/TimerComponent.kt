package org.setu.focussphere.ui.screens.taskTracker

import androidx.compose.runtime.Composable
import com.alltechies.timer.CountDownTimer
import com.alltechies.timer.data.Action
import timber.log.Timber.Forest.i
import kotlin.time.Duration.Companion.seconds

@Composable
fun TimerComponent(
    running: Boolean,
    currentTaskDuration: Long,
    onTaskComplete: () -> Unit
) {

    var x = 45.seconds.inWholeMilliseconds

    i("current duration: $currentTaskDuration")
    i("current duration x: $x")


    if (running) {
        CountDownTimer(
            actionList = listOf(
                Action(currentTaskDuration, "tracking.."),
            ),
            onTimerExpired = {
                onTaskComplete()

            }
        )

    } else {
        CountDownTimer(
            actionList = listOf(
                Action(0.seconds.inWholeMilliseconds, ""),
            ),

        ) {

        }
    }

}