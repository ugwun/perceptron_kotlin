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
        val expected = originalBias * originalWeights[0] * originalWeights[1]

        // Act
        perceptron.train(epochs = 10)
        val actual = perceptron.bias * perceptron.weights[0] * perceptron.weights[1]

        // Assert
        assertNotEquals(expected, actual)
    }

    @Test
    fun getModel() {
        // Prepare
        val perceptron = Perceptron(inputsClassTrue, inputsClassFalse, terminal = terminal)

        // Act
        val model = perceptron.getModel()

        // Assert
        assertTrue(model.contains("0 ="))
        assertTrue(model.contains("*x"))
    }

    @Test
    fun getSeparationLineCoordinates() {
        // Prepare
        val perceptron = Perceptron(inputsClassTrue, inputsClassFalse, terminal = terminal)
        Mockito.`when`(terminal.writer()).thenReturn(PrintWriter(System.out))

        // Act
        val coordinates = perceptron.getSeparationLineCoordinates()

        // Assert
        assertEquals(2, coordinates.size)
        assertEquals(2, coordinates[0].size)
        assertEquals(2, coordinates[1].size)
        assertEquals(0.0, coordinates[0][0])
        assertEquals(1.0, coordinates[0][1])
    }
}