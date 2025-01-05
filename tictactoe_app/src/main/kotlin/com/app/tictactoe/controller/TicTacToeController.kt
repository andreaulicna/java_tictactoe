package com.app.tictactoe.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails

import com.app.tictactoe.repository.MatchRepository
import com.app.tictactoe.repository.UserRepository

import com.app.tictactoe.game.AIPlayer
import com.app.tictactoe.game.Board
import com.app.tictactoe.game.Player
import com.app.tictactoe.game.GameMode
import com.app.tictactoe.game.GameResult
import com.app.tictactoe.game.LocalPlayer

import com.app.tictactoe.model.Match
import com.app.tictactoe.model.User

import java.util.Random
import java.time.LocalDateTime

@Controller
class TicTacToeController @Autowired constructor(
    private val matchRepository: MatchRepository,
    private val userRepository: UserRepository
) {

    private var board = Board()
    private var currentPlayer: Player? = null
    private var playerX: Player? = null
    private var playerO: Player? = null
    private var gameOver = false
    private var mode: GameMode? = null

    @ModelAttribute
    fun addUserDetailsToModel(@AuthenticationPrincipal userDetails: UserDetails?, model: Model) {
        userDetails?.let {
            model.addAttribute("user", it)
        }
    }

    @PostMapping("/startGame")
    fun startGame(@RequestParam mode: GameMode, model: Model): String {
        this.mode = mode
        initializePlayers()
        board = Board()
        gameOver = false

        // If the current player is an AI player, make the AI move immediately
        if (currentPlayer is AIPlayer) {
            (currentPlayer as AIPlayer).makeMove(board)
            if (!checkWinnerOrTie(model)) {
                switchPlayer()
            }
        }

        model.addAttribute("board", board)
        model.addAttribute("currentPlayer", currentPlayer)
        model.addAttribute("gameOver", gameOver)
        return "game"
    }

    @PostMapping("/move")
    fun makeMove(@RequestParam row: Int, @RequestParam col: Int, model: Model, @AuthenticationPrincipal userDetails: UserDetails): String {
        if (!gameOver) {
            if (board.updateWithNewMove(row, col, currentPlayer?.symbol ?: ' ')) {
                if (!checkWinnerOrTie(model)) {
                    switchPlayer()
                    if (mode == GameMode.AI_PLAY && currentPlayer is AIPlayer) {
                        (currentPlayer as AIPlayer).makeMove(board)
                        if (!checkWinnerOrTie(model)) {
                            switchPlayer()
                        }
                    }
                }
            } else {
                model.addAttribute("message", "This move is not valid")
            }
        }

        if (gameOver && mode == GameMode.AI_PLAY) {
            val user = userRepository.findByUsername(userDetails.username)
            val result = determineResult()
            val match = Match(user, result, LocalDateTime.now(), mode!!)
            matchRepository.save(match)
        }

        model.addAttribute("board", board)
        model.addAttribute("currentPlayer", currentPlayer)
        model.addAttribute("gameOver", gameOver)
        return "game"
    }

    @PostMapping("/reset")
    fun resetGame(model: Model): String {
        board = Board()
        gameOver = false
        initializePlayers()

        // If the current player is an AI player, make the AI move immediately
        if (currentPlayer is AIPlayer) {
            (currentPlayer as AIPlayer).makeMove(board)
            if (!checkWinnerOrTie(model)) {
                switchPlayer()
            }
        }

        model.addAttribute("board", board)
        model.addAttribute("currentPlayer", currentPlayer)
        model.addAttribute("message", "Game has been reset. Let's play again!")
        model.addAttribute("gameOver", gameOver)
        return "game"
    }

    private fun initializePlayers() {
        val random = Random()
        if (random.nextBoolean()) {
            playerX = LocalPlayer('X')
            playerO = if (mode == GameMode.LOCAL_PLAY) LocalPlayer('O') else AIPlayer('O')
        } else {
            playerO = LocalPlayer('O')
            playerX = if (mode == GameMode.LOCAL_PLAY) LocalPlayer('X') else AIPlayer('X')
        }
        currentPlayer = playerX
    }

    private fun switchPlayer() {
        currentPlayer = if (currentPlayer == playerX) playerO else playerX
    }

    private fun checkWinnerOrTie(model: Model): Boolean {
        val winner = board.checkWinner()
        return if (winner != ' ') {
            model.addAttribute("message", "Player ${currentPlayer?.symbol} wins!")
            gameOver = true
            true
        } else if (board.isFull()) {
            model.addAttribute("message", "The game is a tie!")
            gameOver = true
            true
        } else {
            false
        }
    }

    private fun determineResult(): GameResult {
        val winner = board.checkWinner()
        return when {
            winner == ' ' -> GameResult.TIE
            currentPlayer is AIPlayer -> if (winner == currentPlayer?.symbol) GameResult.LOSS else GameResult.WIN
            winner == currentPlayer?.symbol -> GameResult.WIN
            else -> GameResult.LOSS
        }
    }
}
