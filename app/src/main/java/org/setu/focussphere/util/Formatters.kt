package org.setu.focussphere.util

import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Formatters {

    fun formatDateTimeToDayMonthYear(dateTime: LocalDateTime): String {
        return dateTime.format(DateTimeFormatter.ofPattern("d MMM uuuu"))
    }

/*    note: cannot use toMinutesPart() in this formatter because
      min sdk of 30 does not support this function */

    fun formatDuration(duration: Duration): String {
        val hours = duration.toHours()
        val minutes = duration.toMinutes()
        val minutesPart = (duration.seconds % 3600) / 60

        return buildString {
            if (hours > 0) {
                append("$hours hr")
                if (hours > 1) append("s")
                if (minutesPart > 0) append (" ")
            }
            if (minutes > 0) {
                append("$minutesPart min")
                if (minutes > 1) append("s")
            }
        }
    }

}