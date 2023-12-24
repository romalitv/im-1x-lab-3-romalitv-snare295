package com.lab3
import java.io.File
import java.io.FileWriter

interface MainOutput {
  fun printLine(msg: String)
}

interface IO {
  fun ifFileExist(filePath: String): Boolean
  fun readFileAsString(filePath: String): String
  fun writeOutputToFile(filePath: String, output: String)
  fun ifFileEmpty(filePath: String) : Boolean
}

val stdFileOutputPath : String = "src/main/resources/output.txt"

fun main(args: Array<String>) {
  val output = object : MainOutput {
    override fun printLine(msg: String) = println(msg)
  }

  val ioController = object : IO {
    override fun ifFileExist(filePath: String): Boolean = File(filePath).exists()
    override fun readFileAsString(filePath: String): String = File(filePath).readText()
    override fun writeOutputToFile(filePath: String, output: String) {
      FileWriter(filePath, true).use { it.write(output) }
    }
    override fun ifFileEmpty(filePath: String): Boolean = File(stdFileOutputPath).readText().isEmpty()
  }

  mainHandler(args, output, ioController)
}

fun mainHandler(args: Array<String>, output: MainOutput, ioObj: IO) {

  val errorMessages = ErrorMessages()

  if (args.isEmpty()) {
    output.printLine(errorMessages.noArgs)
    return
  }

  val inputFilePath = args[0]
  if (!ioObj.ifFileExist(inputFilePath)) {
    output.printLine(errorMessages.inputFileDoesNotExist)
    return
  }

  if(!ioObj.ifFileEmpty(stdFileOutputPath)){
    output.printLine(errorMessages.outpuFileIsntEmpty)
    return
  }

  val field = try {
    parseGameField(ioObj.readFileAsString(inputFilePath))
  } catch (e: Exception) { null }

  if (field == null) {
    output.printLine(errorMessages.inputFileContainsSmthWrong)
  } else if(field is GameField) {
    val finalField = returnGameField(field)
    output.printLine(finalField)

    //Write to file
    ioObj.writeOutputToFile(stdFileOutputPath, finalField)

  }
}

class ErrorMessages {
  val noArgs = "To play Tertrisss(R) you need to create a file\n" +
               "that contains height, width, and field with pieces\n" +
               "Example:\n" +
               "5 5\n" +
               ".....\n" +
               ".ppp.\n" +
               "..p..\n" +
               ".#.#.\n" +
               "#####\n"

  val inputFileDoesNotExist = "Input file doesn't exist"
  val inputFileContainsSmthWrong = "Input file contains something wrong"
  val outpuFileIsntEmpty =  "Output file is not empty\nPlease create new or delete content of file"
}
