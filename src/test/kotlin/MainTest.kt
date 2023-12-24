import com.lab3.IO
import com.lab3.MainOutput
import com.lab3.ErrorMessages
import com.lab3.mainHandler
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class MainTest {
    private val errorMessagesCntr = ErrorMessages()
    val messages = mutableListOf<String>()
    private val outputMock: MainOutput = object : MainOutput {
        override fun printLine(msg: String) {
            messages.add(msg)
        }
    }

    @BeforeEach
    internal fun setUp() {
        messages.clear()
    }

    @Test
    internal fun `it should show How-to-use instructions if there is no input file param`() {
        // Given
        val fsMock = object : IO {
            override fun ifFileExist(filePath: String): Boolean {
                throw RuntimeException("ifFileExist has been called unexpectedly")
            }
            override fun readFileAsString(filePath: String): String {
                throw RuntimeException("readFileAsString has been called unexpectedly")
            }
            override fun writeOutputToFile(filePath: String, output: String) = Unit
            override fun ifFileEmpty(filePath: String): Boolean = true
        }

        // When
        mainHandler(emptyArray(), outputMock, fsMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(errorMessagesCntr.noArgs, messages[0])
    }

    @Test
    internal fun `it should check if input file exists and if not then show a message`() {
        // Given
        val ioMock = object : IO {
            override fun ifFileExist(filePath: String): Boolean = false
            override fun readFileAsString(filePath: String): String {
                throw RuntimeException("readFileAsString has been called unexpectedly")
            }
            override fun writeOutputToFile(filePath: String, output: String) = Unit
            override fun ifFileEmpty(filePath: String): Boolean = true

        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, ioMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(errorMessagesCntr.inputFileDoesNotExist, messages[0])
    }

    @Test
    internal fun `it should parse input file and throw an error if it is wrong`() {
        // Given
        val ioMock = object : IO {
            override fun ifFileExist(filePath: String): Boolean = true
            override fun readFileAsString(filePath: String): String = "Wrong input file body"
            override fun writeOutputToFile(filePath: String, output: String) = Unit
            override fun ifFileEmpty(filePath: String): Boolean = true

        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, ioMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(errorMessagesCntr.inputFileContainsSmthWrong, messages[0])
    }

    @Test
    internal fun `it should print final field state`() {
        // Given
        val ioMock = object : IO {
            override fun ifFileExist(filePath: String): Boolean = true
            override fun readFileAsString(filePath: String): String =
                "3 4\n" +
                        ".p.p\n" +
                        "....\n" +
                        "#.#."
            override fun writeOutputToFile(filePath: String, output: String) = Unit
            override fun ifFileEmpty(filePath: String): Boolean = true

        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, ioMock)

        // Then
        assertEquals(1, messages.size)

        val finalBoard =
            "....\n" +
                    "....\n" +
                    "#p#p\n"
        assertEquals(finalBoard , messages[0])
    }

    @Test
    internal fun `it should write to file final field state`() {
        // Given
        var outputFileString : String =""
        val ioMock = object : IO {
            override fun ifFileExist(filePath: String): Boolean = true
            override fun readFileAsString(filePath: String): String =
                "3 4\n" +
                        ".p.p\n" +
                        "....\n" +
                        "#.#."
            override fun writeOutputToFile(filePath: String, output: String) {
                outputFileString = output
            }
            override fun ifFileEmpty(filePath: String): Boolean = true

        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, ioMock)

        // Then
        assertEquals(1, messages.size)

        val finalBoard =
            "....\n" +
                    "....\n" +
                    "#p#p\n"
        assertEquals(finalBoard , outputFileString)
    }

    @Test
    internal fun `it should verify that file is empty`() {
        // Given
        var outputFileString : String ="."
        val ioMock = object : IO {
            override fun ifFileExist(filePath: String): Boolean = true
            override fun readFileAsString(filePath: String): String =
                "3 4\n" +
                        ".p.p\n" +
                        "....\n" +
                        "#.#."
            override fun writeOutputToFile(filePath: String, output: String) {
                outputFileString = output
            }
            override fun ifFileEmpty(filePath: String): Boolean = false
        }

        // When
        mainHandler(arrayOf("input.txt"), outputMock, ioMock)

        // Then
        assertEquals(1, messages.size)
        assertEquals(messages[0] , errorMessagesCntr.outpuFileIsntEmpty)
    }
}