package com.lab3
import java.io.File

interface MainOutput {
  fun printLine(msg: String)
}

interface IO {
  fun ifFileExist(filePath: String): Boolean
  fun readFileAsString(filePath: String): String
}

fun main(args: Array<String>) {
  val output = object : MainOutput {
    override fun printLine(msg: String) = println(msg)
  }

  val ioController = object : IO {
    override fun ifFileExist(filePath: String): Boolean = File(filePath).exists()
    override fun readFileAsString(filePath: String): String = File(filePath).readText()
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

  val field = try {
    parseGameField(ioObj.readFileAsString(inputFilePath))
  } catch (e: Exception) { null }

  if (field == null) {
    output.printLine(errorMessages.inputFileContainsSmthWrong)
  } else if(field is GameField){
    output.printLine(returnGameField(field))
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
}
