package com.lab3

import java.text.Normalizer.Form

fun parseGameField(inputStr: String) : GameField {
    val lines = inputStr.lines()
    val sizeY = lines[0].split(' ')[0].toInt()
    val sizeX = lines[0].split(' ')[1].toInt()

    println(sizeX)
    println(sizeY)



    val squares = mutableSetOf<Pin>()
    for ( numberX in 0 until sizeX ){
        for ( numberY in 0 until sizeY){
            if (lines[numberY + 1][numberX] == 'p'){
                squares += PinFigure(numberX, numberY)
//                println("$numberX + $numberY = p")
            }
            if (lines[numberY + 1][numberX] == '#'){
                squares += PinBlock(numberX, numberY)
//                println("$numberX + $numberY = #")
            }
        }
    }


    return GameField(sizeX, sizeY, squares)
}

fun returnGameField(parseGameField: GameField) : String {


    val gameField : GameField = parseGameField

    gameField.moveFiguresEnd()

    val x = gameField.getSizeX()
    val y = gameField.getSizeY()

    val matrix = Array(y) { Array(x) { "." } }

    val setOfPin = gameField.getFieldPins()

    for (element in setOfPin){
//       println(element)
        if (element is PinFigure){
            matrix[element.y][element.x] = "p"
        } else if (element is PinBlock){
            matrix[element.y][element.x] = "#"
        }
    }

    var finalRes : String = ""

    for (element in matrix) {
        for (elem in element) {
            finalRes += elem
        }
        finalRes += "\n"
    }




    return finalRes
}