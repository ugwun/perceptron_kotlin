package com.csadovsky.perceptron

import org.jline.terminal.Terminal

/**
 * Perceptron to recognize two different classes
 *
 * Two imaginary sets of data for two classes. Each class has two features. The first feature is the x coordinate
 * and the second feature is the y coordinate. The data is stored in an array of arrays. Each array is a data point.
 * More features can be added, however they cannot be visualized in a 2D graph.
 *
 * The perceptron will converge to a solution. However, if the data is not linearly separable, the perceptron will not
 * converge to a solution. In that case, the perceptron will continue to update the weights until the maximum number of
 * iterations(epochs) is reached.
 *
 * Learning rate is the step size for the perceptron to take when updating the weights. If the learning rate is too
 * large, the perceptron will overshoot the minimum. If the learning rate is too small, the perceptron will take too
 * long to converge to a solution.
 *
 * Error is the difference between the expected output and the actual output. The error is used to update the weights.
 * Learning rate is then multiplied by the error to determine the step size for the perceptron to take when updating
 * the weights. Error alone is not used to update the weights because the error can be very large and the step size
 * would be too large. The error is also used to determine if the perceptron has converged to a solution by checking
 * if the error is 0.
 */
class Perceptron(
    val inputsClass1: Array<DoubleArray>,
    val inputsClass2: Array<DoubleArray>,
    var learningRate: Double = 0.1,
    private val terminal: Terminal
) {

    init {
        if (inputsClass1.isEmpty() || inputsClass2.isEmpty()) {
            throw IllegalArgumentException("Inputs must not be empty")
        }

        val inputs = inputsClass1 + inputsClass2

        if (inputs.any { it.size != inputs[0].size }) {
            throw IllegalArgumentException("All inputs must have the same number of features")
        }
    }

    // Initialize weights to random values
    var weights = DoubleArray(inputsClass1[0].size) { Math.random() }
        private set

    // Initialize bias to random value
    var bias = Math.random()
        private set

    // Train perceptron
    fun train(epochs: Int = 10, onUpdate: (Array<DoubleArray>) -> Unit = {}
              ) {
        for (i in 0..epochs) {
            // Train on class 1
            for (input in inputsClass1) {
                train(input, 1, onUpdate)
            }

            // Train on class 2
            for (input in inputsClass2) {
                train(input, -1, onUpdate)
            }
        }
    }

    // Train perceptron on a single input
    private fun train(input: DoubleArray, expected: Int, onUpdate: (Array<DoubleArray>) -> Unit = {}) {
        // Calculate output
        val output = calculateOutput(input)

        // Calculate error
        val error = expected - output

        // Update weights
        for (i in 0 until weights.size) {
            weights[i] += learningRate * error * input[i]
            onUpdate(getSeparationLineCoordinates())
        }

        // Update bias
        bias += learningRate * error
    }

    // Calculate output of perceptron
    fun calculateOutput(input: DoubleArray): Int {
        // Calculate weighted sum
        var sum = 0.0
        for (i in 0 until weights.size) {
            sum += weights[i] * input[i]
        }

        // Add bias
        sum += bias

        // Activation function
        return if (sum > 0) 1 else -1
    }

    // get model
    fun getModel(): String {
        var model = "0 = "
        for (i in weights.indices) {
            model += "${weights[i]}*x${i+1} + "
        }
        model += bias

        return model
    }

    fun getSeparationLineCoordinates(): Array<DoubleArray> {

        if (weights.size != 2) {
            throw IllegalArgumentException("Cannot visualize data with more than 2 features.")
        }

        val x1Coordinate = 0.0
        val y1Coordinate = -bias / weights[1]
        val x2Coordinate = 1.0
        val y2Coordinate = (-bias - weights[0]) / weights[1]

        terminal.writer().println("Separation line Coordinates:\nx1: $x1Coordinate, y1: $y1Coordinate, x2: $x2Coordinate, y2: $y2Coordinate")

        return arrayOf(doubleArrayOf(x1Coordinate, x2Coordinate), doubleArrayOf(y1Coordinate, y2Coordinate))
    }
}