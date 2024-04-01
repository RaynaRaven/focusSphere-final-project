package org.setu.focussphere.ui.screens.reports

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer
import com.github.tehras.charts.line.renderer.point.FilledCircularPointDrawer
import com.github.tehras.charts.line.renderer.yaxis.SimpleYAxisDrawer
import com.github.tehras.charts.piechart.animation.simpleChartAnimation
import org.setu.focussphere.data.entities.TaskCompletion

@Composable
fun TasksPlotChart(
    completionStats: List<TaskCompletion>,
    modifier: Modifier
) {
    val points = completionStats.map { stat ->
        LineChartData.Point(value = stat.accuracy, label = stat.accuracy.toString())
    }

    val lineChartData = LineChartData(
        points = points,
        padBy = 10f,
        startAtZero = true,
        lineDrawer = SolidLineDrawer()
    )

    LineChart(
        linesChartData = listOf(lineChartData),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        animation = simpleChartAnimation(),
        yAxisDrawer = SimpleYAxisDrawer(),
        pointDrawer = FilledCircularPointDrawer(),
        horizontalOffset = 5f,
        labels = completionStats.map { it.accuracy.toString() },
    )

}