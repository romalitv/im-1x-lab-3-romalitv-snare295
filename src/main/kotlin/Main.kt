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
    override fun printLine(msg: String) {
      println(msg)
    }
  }

  val ioController = object : IO {
    override fun ifFileExist(filePath: String): Boolean = File(filePath).exists()
    override fun readFileAsString(filePath: String): String = File(filePath).readText()
  }

  mainHandler(args, output, ioController)
}

fun mainHandler(args: Array<String>, output: MainOutput, ioObj: IO) {
  if (args.isEmpty()) {
    output.printLine(Messages.noArgs)
    return
  }

  val inputFilePath = args[0]
  if (!ioObj.ifFileExist(inputFilePath)) {
    output.printLine(Messages.inputFileDoesNotExist)
    return
  }

  val field = try {
    parseGameField(ioObj.readFileAsString(inputFilePath))
  } catch (e: Exception) {
    null
  }

  if (field == null) {
    output.printLine(Messages.inputFileContainsSmthWrong)
  } else if(field is GameField){
    output.printLine(returnGameField(field))
  }
}

object Messages {
  val noArgs = """
    To play Tertrisss(R) you need to create file
    that contains height, width, and field with pieces
    Example:
    5 5
    .....
    .ppp.
    ..p..
    .#.#.
    #####
    > tetrisss input.txt
  """.trimIndent()

  val inputFileDoesNotExist = """
    Input file doesn't exist
  """.trimIndent()

  val inputFileContainsSmthWrong = """
    inputFileContainsSmthWrong
  """.trimIndent()
}