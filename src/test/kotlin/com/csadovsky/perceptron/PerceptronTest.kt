package com.csadovsky.perceptron

import org.jline.terminal.Terminal
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.jupiter.MockitoExtension
import java.io.PrintWriter

@ExtendWith(MockitoExtension::class)
class PerceptronTest {

    @Mock
    private lateinit var terminal: Terminal

    private val inputsClassTrue = arrayOf(
        doubleArrayOf(1.0, 1.0)
    )

    private val inputsClassFalse = arrayOf(
        doubleArrayOf(0.0, 0.0),
        doubleArrayOf(0.0, 1.0),
        doubleArrayOf(1.0, 0.0)
    )

    @Test
    fun train() {
        // Prepare
        Mockito.`when`(terminal.writer()).thenReturn(PrintWriter(System.out))
        val perceptron = Perceptron(inputsClassTrue, inputsClassFalse, terminal = terminal)
        val originalWeights = perceptron.weights.copyOf()
        val originalBias = perceptron.bias

        // Act
        perceptron.train(epochs = 10)

        // Assert
        assertNotEquals(originalWeights[0], perceptron.weights[0])
        assertNotEquals(originalWeights[1], perceptron.weights[1])
        assertNotEquals(originalBias, perceptron.bias)
    }

    @Test
    fun calculateOutput() {
    }

    @Test
    fun getModel() {
    }

    @Test
    fun getSeparationLineCoordinates() {
    }
}