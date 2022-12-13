package com.csadovsky.perceptron

import org.assertj.core.api.Assertions.assertThat
import org.awaitility.Awaitility.await
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.test.ShellAssertions
import org.springframework.shell.test.ShellTestClient
import org.springframework.shell.test.autoconfigure.ShellTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.DirtiesContext.ClassMode
import java.util.concurrent.TimeUnit

@ShellTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class PerceptronCLIIntegrationTest {

    @Autowired
    lateinit var shellTestClient: ShellTestClient

    @Test
    fun `sanity test`() {
        val session = shellTestClient.interactive().run()

        session.write(session.writeSequence().text("help").carriageReturn().build())

        await().atMost(2, TimeUnit.SECONDS).untilAsserted {
            ShellAssertions.assertThat(session.screen()).containsText("shell:>")
            ShellAssertions.assertThat(session.screen())
                .containsText("train")
            ShellAssertions.assertThat(session.screen())
                .containsText("predict")
        }
    }

    @Test
    fun `train perceptron`() {
        val session = shellTestClient.interactive().run()

        session.write(session.writeSequence().text("train -s false -e 1").carriageReturn().build())

        await().atMost(2, TimeUnit.SECONDS).untilAsserted {
            ShellAssertions.assertThat(session.screen()).containsText("shell:>")
            ShellAssertions.assertThat(session.screen()).containsText("Separation line Coordinates")
            ShellAssertions.assertThat(session.screen()).containsText("Weights:")
            ShellAssertions.assertThat(session.screen()).containsText("Bias:")
        }
    }

    @Test
    fun `predict perceptron`() {
        val session = shellTestClient.interactive().run()

        session.write(session.writeSequence().text("train -s false -e 1").carriageReturn().build())

        session.write(session.writeSequence().text("predict").carriageReturn().build())

        await().atMost(2, TimeUnit.SECONDS).untilAsserted {
            ShellAssertions.assertThat(session.screen()).containsText("shell:>")
            ShellAssertions.assertThat(session.screen()).containsText("Calculate outputs:")
            ShellAssertions.assertThat(session.screen()).containsText("Input:")
            ShellAssertions.assertThat(session.screen()).containsText("Model:")
        }
    }

}