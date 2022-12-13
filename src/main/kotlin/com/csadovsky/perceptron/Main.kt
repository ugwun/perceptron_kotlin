package com.csadovsky.perceptron

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class PerceptronCLIApplication

fun main() {
    // Spring Shell runs in a headless environment, so we need to explicitly set the headless property to false
    // so that the chart can be displayed. Otherwise, the chart will not be displayed and the program will exit with
    // an error.
    System.setProperty("java.awt.headless", "false")

    SpringApplication.run(PerceptronCLIApplication::class.java)
}


