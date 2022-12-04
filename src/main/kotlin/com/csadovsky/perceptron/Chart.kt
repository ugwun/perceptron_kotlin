package com.csadovsky.perceptron

import org.knowm.xchart.QuickChart
import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.XYChart
import org.knowm.xchart.XYSeries
import javax.swing.JFrame

/**
 * Chart to visualize the perceptron
 *
 * @param separationLineCoordinates The coordinates of the separation line
 * @param inputsClass1 The inputs for class 1
 * @param inputsClass2 The inputs for class 2
 *
 */
class Chart(private val separationLineCoordinates: Array<DoubleArray>,
            private val inputsClass1: Array<DoubleArray>,
            private val inputsClass2: Array<DoubleArray>) {

    private val chartJFrame: JFrame
    private val xyChart: XYChart

    init {
        val separationLineXCoordinates = separationLineCoordinates.get(0)
        val separationLineYCoordinates = separationLineCoordinates.get(1)

        // Create chart with separation line
        xyChart = QuickChart.getChart(
            "Boolean AND",
            "X",
            "Y",
            "Separation Line",
            separationLineXCoordinates,
            separationLineYCoordinates
        )

        // Add TRUE data points
        xyChart.addSeries(
            "True",
            inputsClass1.map { it[0] }.toDoubleArray(),
            inputsClass1.map { it[1] }.toDoubleArray()
        )
            .xySeriesRenderStyle = XYSeries.XYSeriesRenderStyle.Scatter
        // Add FALSE data points
        xyChart.addSeries(
            "False",
            inputsClass2.map { it[0] }.toDoubleArray(),
            inputsClass2.map { it[1] }.toDoubleArray()
        )
            .xySeriesRenderStyle = XYSeries.XYSeriesRenderStyle.Scatter

        // Show chart
        chartJFrame = SwingWrapper(xyChart).displayChart()

    }

    /**
     * Update chart
     *
     * @param separationLineCoordinates The coordinates of the separation line
     */
    fun update(separationLineCoordinates: Array<DoubleArray>) {
        val separationLineXCoordinates = separationLineCoordinates.get(0)
        val separationLineYCoordinates = separationLineCoordinates.get(1)

        // give the user a chance to see the update of the chart
        Thread.sleep(200)

        // new separation line coordinates
        xyChart.updateXYSeries(
            "Separation Line",
            separationLineXCoordinates,
            separationLineYCoordinates,
            null
        )
        chartJFrame.repaint()
    }

}