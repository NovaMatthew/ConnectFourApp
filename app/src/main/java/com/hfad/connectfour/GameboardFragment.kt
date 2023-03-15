/**
 * Main gameboard class to run console game
 * @author Matthew Nova
 * @assignment Connect Four Assignment 2
 * @due 2/25/2022
 */
package com.hfad.connectfour

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import FourInARow
import GameConstants.COLS
import GameConstants.ROWS
import IGame
import android.os.Handler
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext

//import MainGame


class GameboardFragment : Fragment() {
    // ...
    private lateinit var restartButton: Button
    private lateinit var namedDisplay: TextView
    private lateinit var whosTurn: TextView
    private lateinit var buttons: Array<Array<Button>>
    private lateinit var result: TextView
    private var username: String? = ""

    val FIR_board = FourInARow()
    var currentState = 100
    var currentPlayer = 1



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gameboard, container, false)

    //Player name
        // This code retrieves the user input from the splash screen
        // and displays it in the text view underneath the game board
        namedDisplay = view.findViewById(R.id.name)
        val args = this.arguments
        val inputData = args?.get("data")
        namedDisplay.text = inputData.toString()

        // variable for text view that indicates whose turn it is
        whosTurn = view.findViewById(R.id.whos_turn)

        result = view.findViewById(R.id.result)

        //sets initial text indicator for user players' turn
        setTurn(currentPlayer)

        // initializes restartButton
        restartButton = view.findViewById(R.id.restart_button)

        // calls restartGame method on user click of the restart button
        restartButton.setOnClickListener {
            restartGame()
        }
        // creates identification for UI buttons
        buttons = Array(6) { row ->
            Array(6) { column ->

                val buttonId = "button_${row * 6 + column}"
                view.findViewById<Button>(
                    resources.getIdentifier(
                        buttonId, "id", requireContext().packageName
                    )
                )
            }
        }


// Loops through UI buttons reacting to appropriate button click
        for (i in 0 until 6) {
            for (j in 0 until 6) {
                val button = buttons[i][j]
                button?.setOnClickListener {
                    if (currentPlayer == 1) {
                        // Human player's turn
                        setTurn(currentPlayer)
                        // Console output for debugging
                        val row = buttons.indexOf(buttons.find { it.contains(button) })
                        val col = buttons[row].indexOf(button)
                        Log.d("GameboardFragment", "Button clicked at row $row, col $col")

                        //uses backend logic to set player move
                        FIR_board.setMove(currentPlayer, row * COLS + col)

                        //UI setMove
                        setMove(button, currentPlayer)


                        currentPlayer = 2

                        // to display AI's turn
                        setTurn(currentPlayer)

                        // AI player's turn including a delay for enhanced user experience
                        Handler().postDelayed({

                            //gets back end logic for random AI location move
                            val aiMove = FIR_board.computerMove
                            //sets AI move
                            FIR_board.setMove(currentPlayer, aiMove)

                            // sets AI's UI location
                            val aiButton = buttons[aiMove / COLS][aiMove % COLS]
                            setMove(aiButton, currentPlayer)

                            currentPlayer = 1

                            setTurn(currentPlayer)

                            // checks for win state
                            val winner = FIR_board.checkForWinner()
                            if (winner != 0) {
                                when (winner) {
                                    2 -> {
                                        Log.d("GameboardFragment", "RED WINS!")
                                        result.text = "RED WINS!"
                                        currentState = GameConstants.RED_WON
                                    }
                                    3 -> {
                                        Log.d("GameboardFragment", "YOU LOSE, BLACK WINS!")
                                        result.text = "YOU LOSE, BLACK WINS!"
                                        currentState = GameConstants.BLUE_WON
                                    }
                                    1 -> {
                                        Log.d("GameboardFragment", "IT'S A TIE")
                                        result.text = "IT'S A TIE"
                                        currentState = GameConstants.TIE
                                    }
                                }
                                // On game over reveals reset button and disables game board
                                restartButton.visibility = View.VISIBLE
                                for (i in 0 until ROWS) {
                                    for (j in 0 until COLS) {
                                        buttons[i][j].isEnabled = false
                                    }
                                }
                            }

                        }, 1000)
                    }
                }
            }
        }
        return view
    }

    // Method for restarting the game board
    private fun restartGame() {
        FIR_board.clearBoard()
        currentState = GameConstants.PLAYING
        currentPlayer = 1
        result.text = ""
        for (i in 0 until ROWS) {
            for (j in 0 until COLS) {
                buttons[i][j].isEnabled = true
                buttons[i][j].setBackgroundColor(requireContext().getColor(R.color.purple_700))
            }
        }
        restartButton.visibility = View.GONE
    }

    // method for displaying whose turn it is
    fun setTurn(currentPlayer: Int) {
        if (currentPlayer == 1) {
            whosTurn.text = "Your Turn"
        } else if (currentPlayer == 2) {
            whosTurn.text = "AI Turn"
        }
    }

    //method for UI game board locations played
    fun setMove(button: Button, player: Int) {
        button.isEnabled = false // disable the button
        if (player == 1) {
            button.setBackgroundColor(requireContext().getColor(R.color.red))

        } else {
            button.setBackgroundColor(requireContext().getColor(R.color.black))

        }
    }
}


