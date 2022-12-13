package com.csadovsky.perceptron

import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

/*
 * The first data point in the first class is [1.0, 1.0]. The first data point in the second class is [0.0, 0.0],
 * second data point in the second class is [0.0, 1.0], etc. The example given here is a linearly separable data set
 * - simulated boolean AND.
 *
 * Class 1: [1.0, 1.0] => we are implicitly saying that the expected output for this data point is 1 (meaning TRUE)
 * Class 2: [0.0, 0.0], [0.0, 1.0], [1.0, 0.0] => we are implicitly saying that the expected output for these data
 * points is -1 (meaning FALSE)
 *
 * Try to run the program multiple times and see how the perceptron learns to separate the two classes differently each
 * time. This is because the weights are initialized randomly each time.
 */
@ShellComponent
class PerceptronCLI {
    // Define a field for the perceptron instance.
    private lateinit var perceptron: Perceptron

    // Inputs for class 1 - AND => TRUE - in Perceptron represented as 1
    val inputsClassTrue = arrayOf(
        doubleArrayOf(1.0, 1.0)
    )

    // Inputs for class 2 - AND => FALSE - in Perceptron represented as -1
    val inputsClassFalse = arrayOf(
        doubleArrayOf(0.0, 0.0),
        doubleArrayOf(0.0, 1.0),
        doubleArrayOf(1.0, 0.0)
    )

    @ShellMethod("Train the perceptron on the AND data set")
    fun train(
        @ShellOption(value = ["-e", "--epochs"], defaultValue = "10", help = "The number of epochs to train for.")
        epochs: Int,
        @ShellOption(value = ["-l", "--learning-rate"], defaultValue = "0.1", help = "The learning rate to use.")
        learningRate: Double,
        @ShellOption(value = ["-s", "--show-chart"], defaultValue = "true", help = "Whether to show a chart.")
        showChart: Boolean
    ) {
        perceptron = Perceptron(inputsClassTrue, inputsClassFalse, learningRate)

        // Train perceptron
        when (showChart) {
            true -> {
                // Create initial separation line chart with initial unconverged weights
                val chart = Chart(perceptron.getSeparationLineCoordinates(), inputsClassTrue, inputsClassFalse)
                perceptron.train(epochs) { chart.update(perceptron.getSeparationLineCoordinates()) }
            }
            false -> perceptron.train(epochs)
        }

        // Print final weights and bias
        println("Weights: ${perceptron.weights.contentToString()}")
        println("Bias: ${perceptron.bias}")
    }

    @ShellMethod("Make a prediction with the perceptron for the whole AND dataset.")
    fun predict() {

        println("Calculate outputs:")
        for (input in inputsClassTrue) {
            println("Input: ${input.contentToString()}; Guessed output: ${perceptron.calculateOutput(input)}")
        }


        for (input in inputsClassFalse) {
            println("Input: ${input.contentToString()} Guessed output: ${perceptron.calculateOutput(input)}")
        }

        // By using this formula we can calculate the output of the perceptron for any input
        println("Model: ${perceptron.getModel()}")
    }
}