package com.example.personalfinancemanager.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.extensions.formatToSinglePrecision
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.example.personalfinancemanager.components.chart.ChartComponent
import com.example.personalfinancemanager.database.entities.BalanceDbEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ChartScreen(component: ChartComponent) {
    val model by component.model.subscribeAsState()

    if (model.balanceHistory.isNotEmpty()) {
        val lineChartData = LineChartData(
            linePlotData = LinePlotData(
                lines = listOf(
                    Line(
                        dataPoints = generatePoints(model.balanceHistory),
                        LineStyle(),
                        IntersectionPoint(),
                        SelectionHighlightPoint(),
                        ShadowUnderLine(),
                        SelectionHighlightPopUp()
                    )
                ),
            ),
            xAxisData = generateXAxisData(
                model.balanceHistory,
                MaterialTheme.colorScheme.primary
            ),
            yAxisData = generateYAxisData(
                model.balanceHistory,
                MaterialTheme.colorScheme.primary.compositeOver(Color.White)
            ),
            gridLines = GridLines(),
            backgroundColor = MaterialTheme.colorScheme.onBackground
        )

        Box(Modifier.fillMaxSize()) {
            LineChart(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .height(300.dp),
                lineChartData = lineChartData
            )
        }
    }
}

fun generatePoints(operations: List<BalanceDbEntity>): List<Point> {
    var x = -1f

    return operations.map {
        x += 1f
        Point(
            x = x,
            y = it.amount
        )
    }
}

fun generateXAxisData(
    operations: List<BalanceDbEntity>,
    color: Color
) =
    AxisData.Builder()
        .axisStepSize(100.dp)
        .backgroundColor(color)
        .steps(operations.size - 1)
        .labelData { index ->
            LocalDateTime.parse(operations[index].datetime).format(
                DateTimeFormatter.ofPattern("d MMM uu")
            )
        }
        .labelAndAxisLinePadding(15.dp)
        .build()

fun generateYAxisData(
    operations: List<BalanceDbEntity>,
    color: Color
): AxisData {
    val maxAmount = operations.maxOf { it.amount }
    val minAmount = operations.minOf { it.amount }
    val steps = 5
    val yScale = (maxAmount - minAmount) / steps

    return AxisData.Builder()
        .steps(steps)
        .backgroundColor(color)
        .labelData { i ->
            if (i == 0)
                minAmount.toAmountString()
            else
                (minAmount + (i * yScale)).toAmountString()
        }
        .labelAndAxisLinePadding(5.dp)
        .build()
}

fun Float.toAmountString() = String.format("%.2f₽", this)

