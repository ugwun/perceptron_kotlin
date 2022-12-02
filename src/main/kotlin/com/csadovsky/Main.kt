package com.csadovsky

import org.knowm.xchart.QuickChart
import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.XYChart
import org.knowm.xchart.XYSeries


/*
 * The first data point in the first class is [1.0, 1.0]. The first data point in the second class is [0.0, 0.0],
 * second data point in the second class is [0.0, 1.0], etc. The example given here is a linearly separable data set
 * - simulated boolean AND.
 *
 * Class 1: [1.0, 1.0] => we are implicitly saying that the expected output for this data point is 1 (meaning TRUE)
 * Class 2: [0.0, 0.0], [0.0, 1.0], [1.0, 0.0] => we are implicitly saying that the expected output for these data
 * points is -1 (meaning FALSE)
 */
fun main() {
    // Inputs for class 1 - AND => TRUE - in Perceptron represented as 1
    val inputsClass1 = arrayOf(
        doubleArrayOf(1.0, 1.0)
    )

    // Inputs for class 2 - AND => FALSE - in Perceptron represented as -1
    val inputsClass2 = arrayOf(
        doubleArrayOf(0.0, 0.0),
        doubleArrayOf(0.0, 1.0),
        doubleArrayOf(1.0, 0.0)
    )

    // Create perceptron
    val perceptron = Perceptron(inputsClass1, inputsClass2)

    // Train perceptron
    perceptron.train()

    // Print weights and bias
    println("Weights: ${perceptron.weights.contentToString()}")
    println("Bias: ${perceptron.bias}")

    // Print outputs
    println("Calculate outputs:")
    for (input in inputsClass1) {
        println("Input: ${input.contentToString()} Output: ${perceptron.calculateOutput(input)}")
    }

    for (input in inputsClass2) {
        println("Input: ${input.contentToString()} Output: ${perceptron.calculateOutput(input)}")
    }

    println("Model: ${perceptron.getModel()}")

    // Plot data
    val separationLineCoordinates = perceptron.getSeparationLineCoordinates()
    val separationLineXCoordinates = separationLineCoordinates.get(0)
    val separationLineYCoordinates = separationLineCoordinates.get(1)

    val chart: XYChart = QuickChart.getChart("Boolean AND",
        "X",
        "Y",
        "Separation Line",
        separationLineXCoordinates,
        separationLineYCoordinates)

    // Add TRUE data points
    chart.addSeries("True",
        inputsClass1.map { it[0] }.toDoubleArray(),
        inputsClass1.map { it[1] }.toDoubleArray())
        .xySeriesRenderStyle = XYSeries.XYSeriesRenderStyle.Scatter
    // Add FALSE data points
    chart.addSeries("False",
        inputsClass2.map { it[0] }.toDoubleArray(),
        inputsClass2.map { it[1] }.toDoubleArray())
        .xySeriesRenderStyle = XYSeries.XYSeriesRenderStyle.Scatter

    SwingWrapper(chart).displayChart()
}