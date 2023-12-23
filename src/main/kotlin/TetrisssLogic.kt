package com.lab3


interface Pin {
    var x : Int
    var y : Int
}

data class PinFigure(override var x : Int, override var y : Int)   : Pin
data class PinBlock(override var x : Int, override var y : Int)     : Pin

data class GameField (
    private val sizeX : Int ,
    private val sizeY : Int,

    private var squares : MutableSet<Pin>,
) {

    init {
        checkForSize()
    }

    private fun checkForSize() {
        for (i in squares) {
            if( i.x > sizeX || i.x < 0){
                throw IllegalArgumentException("Invalid data for GameField, position of Pin is not inbound")
            }

            if( i.y > sizeY || i.y < 0){
                throw IllegalArgumentException("Invalid data for GameField, position of Pin is not inbound")
            }
        }
    }

    private var figurePins : MutableSet<Pin> = sortSquaresFigures()
    private var blockPins : MutableSet<Pin> = sortSquaresBlocks()


    private fun sortSquaresFigures() : MutableSet<Pin>{
        var x : MutableSet<Pin> = mutableSetOf()

        for (i in squares) {
            if(i is PinFigure){
                x.add(i)
            }
        }

        return x
    }

    private fun sortSquaresBlocks() : MutableSet<Pin>{
        var x : MutableSet<Pin> = mutableSetOf()

        for (i in squares) {
            if(i is PinBlock){
                x.add(i)
            }

        }
        return x
    }

    fun getSizeX() : Int {
        return sizeX
    }


    fun getSizeY() : Int {
        return sizeY
    }

    /* Makes move, return false when game ended */
    fun moveFiguresStep() : Boolean{

        for (i in figurePins) {

            //border check
            if(i.y + 1 >= sizeY){
                return false
            } else {
                for (j in blockPins) {

                    //collision check
                    if(i.y+1 == j.y && i.x == j.x){
                        return false
                    }
                }
            }
        }

        if(figurePins.isEmpty()){
            return false
        }


        //if all ok, move all figures
        for (i in figurePins) {
            i.y += 1
        }
        return true
    }

    fun moveFiguresEnd(){

        var i : Boolean = true
        while(i){
            i = moveFiguresStep()
        }

    }

    fun getFieldPins() : Set<Pin>{
        return squares
    }
}

