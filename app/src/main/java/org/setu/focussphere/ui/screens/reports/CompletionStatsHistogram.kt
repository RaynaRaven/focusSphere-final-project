package org.setu.focussphere.ui.screens.reports

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import org.setu.focussphere.data.entities.CompletionStats

@Composable
fun CompletionStatsHistogram(
    completionStats: List<CompletionStats>,
    modifier : Modifier
) {

    val daysOfWeekLabels = listOf("M", "T", "W", "T", "F", "S", "S")

    // pads the completions with empty CompletionStat if there is no data for a particular day
    val stats = List(daysOfWeekLabels.size) { index ->
        val completion = completionStats.find { completion -> completion.dayOfWeek == index}
        completion ?: CompletionStats("", index, 0)
    }

    val bars = stats.map { stat ->
        BarChartData.Bar(
            value = stat.numCompletions.toFloat(),
            color = Color.Green,
            label = daysOfWeekLabels.getOrElse(stat.dayOfWeek) { "Unknown"}
            )
    }

    BarChart(
        barChartData = BarChartData(bars = bars, padBy = 1f),
        modifier = Modifier.fillMaxWidth().height(200.dp)
        )
}
