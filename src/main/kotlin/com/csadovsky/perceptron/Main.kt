package com.csadovsky.perceptron


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

    // Create initial separation line chart with initial unconverged weights
    val chart = Chart(perceptron.getSeparationLineCoordinates(), inputsClass1, inputsClass2)

    // Train perceptron and show visualization
    perceptron.train(chart = chart)

    // Print final weights and bias
    println("Weights: ${perceptron.weights.contentToString()}")
    println("Bias: ${perceptron.bias}")

    // Guess the output for a data point
    println("Calculate outputs:")
    for (input in inputsClass1) {
        println("Input: ${input.contentToString()} Output: ${perceptron.calculateOutput(input)}")
    }

    // Guess the output for a data point
    for (input in inputsClass2) {
        println("Input: ${input.contentToString()} Output: ${perceptron.calculateOutput(input)}")
    }

    // By using this formula we can calculate the output of the perceptron for any input
    println("Model: ${perceptron.getModel()}")

    // Show chart with final separation line
//    Chart(perceptron.getSeparationLineCoordinates(), inputsClass1, inputsClass2)

}

