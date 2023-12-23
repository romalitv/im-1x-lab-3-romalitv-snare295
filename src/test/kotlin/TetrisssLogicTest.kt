import com.lab3.GameField
import com.lab3.PinBlock
import com.lab3.PinFigure
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TetrisssLogicTest {

    @Test
    fun testMoveFiguresStepNoCollision() {
        val figurePin = PinFigure(0, 0)
        val blockPin = PinBlock(1, 1)
        val gameField = GameField(5, 5, mutableSetOf(figurePin, blockPin))

        assertTrue(gameField.moveFiguresStep())

        assertEquals(1, figurePin.y)
        assertEquals(0, figurePin.x)
        assertEquals(1, blockPin.y)
        assertEquals(1, blockPin.y)
    }

    @Test
    fun testMoveFiguresStepEnd() {
        val figurePin = PinFigure(0, 0)
        val blockPin = PinBlock(1, 1)
        val gameField = GameField(5, 5, mutableSetOf(figurePin, blockPin))

        gameField.moveFiguresEnd()

        assertEquals(4, figurePin.y)
        assertEquals(1, blockPin.y)
    }

    @Test
    fun testMoveFiguresStep_Collision() {
        val figurePin = PinFigure(0, 0)
        val blockPin = PinBlock(0, 1)
        val gameField = GameField(5, 5, mutableSetOf(figurePin, blockPin))

        assertFalse(gameField.moveFiguresStep())

        assertEquals(0, figurePin.y)
        assertEquals(1, blockPin.y)
    }

    @Test
    fun testMoveFiguresStep_OutOfBounds() {
        val figurePin = PinFigure(0, 4)
        val gameField = GameField(5, 5, mutableSetOf(figurePin))

        assertFalse(gameField.moveFiguresStep())

        assertEquals(4, figurePin.y)
    }

    @Test
    fun testGetFieldPins() {
        val figurePin = PinFigure(0, 0)
        val blockPin = PinBlock(1, 1)
        val gameField = GameField(5, 5, mutableSetOf(figurePin, blockPin))

        val pins = gameField.getFieldPins()

        assertTrue(pins.contains(figurePin))
        assertTrue(pins.contains(blockPin))
    }
}