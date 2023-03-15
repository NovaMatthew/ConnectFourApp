/**
 * Main class to run console game (Not used in Android APP)
 * @author Matthew Nova
 * @assignment Connect Four Assignment 2
 * @due 2/25/2022
 */
class MainGame {
    val FIR_board = FourInARow()

    /** The entry main method (the program starts here)  */
    fun playGame(): Int {
        var currentState: Int = GameConstants.PLAYING
        var userInput: String = ""
        var integerValue: Int = 0


        //game loop
        do {



            var quit = false
            var clear = false
            //accept user move
            do {
                println("Select a move: ")
                userInput = readLine()!!

                //quits game
                if (userInput == "q") {
                    quit = true
                    break
                }
                //clears board
                if (userInput == "c") {
                    FIR_board.clearBoard()
                    clear = true
                    break
                }
                integerValue = userInput.toInt()

                // Validation check
            } while (integerValue > 35 || integerValue < 0)

            // quits game
            if (quit) {
                break
            }
            // Clears board continues play
            if (clear) {
                playGame()
            }
            // sets player position on board
            FIR_board.setMove(1, integerValue)

            // sets computer position on board
            FIR_board.setMove(2, FIR_board.computerMove)

            // Checks winner
            FIR_board.checkForWinner()

            //Prints winner RED
            if (FIR_board.checkForWinner() == 2) {
                println("RED WINS!")
                currentState = GameConstants.RED_WON

                //Prints winner BLUE
            } else if (FIR_board.checkForWinner() == 3) {
                println("YOU LOSE, BLUE WINS!")
                currentState = GameConstants.BLUE_WON
            }
            //Prints TIE
            else if (FIR_board.checkForWinner() == 1) {
                println("ITS A TIE")
                currentState = GameConstants.TIE
            }
            //Ends game loop
        } while (currentState == GameConstants.PLAYING && userInput != "q")

        println("GAME OVER")


        return currentState
    }
}
