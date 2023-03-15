import GameConstants.BLUE
import GameConstants.BLUE_WON
import GameConstants.COLS
import GameConstants.EMPTY
import GameConstants.PLAYING
import GameConstants.RED_WON
import GameConstants.ROWS
import GameConstants.TIE
import java.text.BreakIterator

/**
 * TicTacToe class implements the interface
 * @author Matthew Nova
 * @assignment Connect Four
 * @due 2/2/2022
 */
class FourInARow
/**
 * clear board and set current player
 */
    : IGame {
    // game board in 2D array initialized to zeros
    private val board = Array(GameConstants.ROWS) { IntArray(GameConstants.COLS) { 0 } }

    // Clears game board
    override fun clearBoard() {
        for (row in 0 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS) {
                board[row][col] = GameConstants.EMPTY
            }
        }
    }

    // Sets player position (both computer and user)
    override fun setMove(player: Int, location: Int) {
        val rowPlay = location / 6
        val colPlay = location % 6

        // sets player location for user or computer
        if (player == 1) {
            board[rowPlay][colPlay] = GameConstants.RED
        } else {
            board[rowPlay][colPlay] = GameConstants.BLUE

        }
    }

    // randomizes computer location
    override val computerMove: Int
        get() {
            var inRange: Boolean = false
            var randEmptySlot: Int = 0
            do {
                randEmptySlot = (0..35).random()
                val rowPlay = randEmptySlot / 6
                val colPlay = randEmptySlot % 6
                if (board[rowPlay][colPlay] == GameConstants.EMPTY) {
                    inRange = true
                }
            } while (!inRange)
            return randEmptySlot
        }

    // check for winner

    override fun checkForWinner(): Int {
        // Check for a horizontal win
        for (r in 0 until ROWS) {
            for (c in 0 until COLS - 3) {
                val cell = board[r][c]
                if (cell != EMPTY && cell == board[r][c + 1] && cell == board[r][c + 2] && cell == board[r][c + 3]) {
                    return if (cell == BLUE) BLUE_WON else RED_WON
                }
            }
        }

        // Check for a vertical win
        for (r in 0 until ROWS - 3) {
            for (c in 0 until COLS) {
                val cell = board[r][c]
                if (cell != EMPTY && cell == board[r + 1][c] && cell == board[r + 2][c] && cell == board[r + 3][c]) {
                    return if (cell == BLUE) BLUE_WON else RED_WON
                }
            }
        }

        // Check for a diagonal win (up-right)
        for (r in 3 until ROWS) {
            for (c in 0 until COLS - 3) {
                val cell = board[r][c]
                if (cell != EMPTY && cell == board[r - 1][c + 1] && cell == board[r - 2][c + 2] && cell == board[r - 3][c + 3]) {
                    return if (cell == BLUE) BLUE_WON else RED_WON
                }
            }
        }

        // Check for a diagonal win (down-right)
        for (r in 0 until ROWS - 3) {
            for (c in 0 until COLS - 3) {
                val cell = board[r][c]
                if (cell != EMPTY && cell == board[r + 1][c + 1] && cell == board[r + 2][c + 2] && cell == board[r + 3][c + 3]) {
                    return if (cell == BLUE) BLUE_WON else RED_WON
                }
            }
        }

        // Check if the board is full
        for (r in 0 until ROWS) {
            for (c in 0 until COLS) {
                if (board[r][c] == EMPTY) {
                    // There's an empty cell, so the game is still playing
                    return PLAYING
                }
            }
        }

        // If we get here, the board is full and there's no winner, so it's a tie
        return TIE
    }


    /**
     * Print the game board
     */
    fun printBoard() {
        for (row in 0 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS) {
                printCell(board[row][col]) // print each of the cells
                if (col != GameConstants.COLS - 1) {
                    print("|") // print vertical partition
                }
            }
            println()
            if (row != GameConstants.ROWS - 1) {
                println("------------------------") // print horizontal partition
            }
        }
        println()
    }

    /**
     * Print a cell with the specified "content"
     * @param content either BLUE, RED or EMPTY
     */
    fun printCell(content: Int) {
        when (content) {
            GameConstants.EMPTY -> print("   ")
            GameConstants.BLUE -> print(" B ")
            GameConstants.RED -> print(" R ")
        }
    }
}
