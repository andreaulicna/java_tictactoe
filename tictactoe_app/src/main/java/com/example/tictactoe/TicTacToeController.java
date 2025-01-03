package com.example.tictactoe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tictactoe.AIPlayer;

import java.util.Random;
import java.util.Scanner;

@Controller
public class TicTacToeController {

    private static final Logger logger = LoggerFactory.getLogger(TicTacToeController.class);

    private Board board = new Board();
    private Player currentPlayer;
    private Player playerX;
    private Player playerO;
    private boolean gameOver;
    private GameMode mode;

    
    public TicTacToeController() {
        
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("board", this.board);
        model.addAttribute("currentPlayer", this.currentPlayer);
        model.addAttribute("gameOver", this.gameOver);
        return "index";
    }

    @GetMapping("/chooseMode")
    public String chooseMode() {
        return "chooseMode";
    }
    @PostMapping("/startGame")
    public String startGame(@RequestParam("mode") GameMode mode, Model model) {
        this.mode = mode;
        initializePlayers();
        this.board = new Board();
        this.gameOver = false;

        // If the current player is an AI player, make the AI move immediately
        if (this.currentPlayer instanceof AIPlayer) {
            AIPlayer aiPlayer = (AIPlayer) this.currentPlayer;
            aiPlayer.makeMove(board);
            if (!checkWinnerOrTie(model)) {
                switchPlayer();
            }
        }

        model.addAttribute("board", this.board);
        model.addAttribute("currentPlayer", this.currentPlayer);
        model.addAttribute("gameOver", this.gameOver);
        return "index";
    }

    @PostMapping("/move")
    public String makeMove(@RequestParam int row, @RequestParam int col, Model model) {
        if (!this.gameOver) {
            if (this.board.makeMove(row, col, this.currentPlayer.getSymbol())) {
                if (!checkWinnerOrTie(model)) {
                    switchPlayer();
                    if (this.mode == GameMode.AI_PLAY && this.currentPlayer instanceof AIPlayer) {
                        AIPlayer aiPlayer = (AIPlayer) this.currentPlayer;
                        aiPlayer.makeMove(board);
                        if (!checkWinnerOrTie(model)) {
                            switchPlayer();
                        }
                    }
                }
            } else {
                model.addAttribute("message", "This move is not valid");
            }
        }
        model.addAttribute("board", this.board);
        model.addAttribute("currentPlayer", this.currentPlayer);
        model.addAttribute("gameOver", this.gameOver);
        return "index";
    }

    @PostMapping("/reset")
    public String resetGame(Model model) {
        this.board = new Board();
        this.gameOver = false;
        initializePlayers();

        // If the current player is an AI player, make the AI move immediately
        if (this.currentPlayer instanceof AIPlayer) {
            AIPlayer aiPlayer = (AIPlayer) this.currentPlayer;
            aiPlayer.makeMove(board);
            if (!checkWinnerOrTie(model)) {
                switchPlayer();
            }
        }
        
        model.addAttribute("board", this.board);
        model.addAttribute("currentPlayer", this.currentPlayer);
        model.addAttribute("message", "Game has been reset. Let's play again!");
        model.addAttribute("gameOver", this.gameOver);
        return "index";
    }

    private void initializePlayers() {
        Random random = new Random();
        if (random.nextBoolean()) {
            this.playerX = new LocalPlayer('X', new Scanner(System.in));
            this.playerO = (this.mode == GameMode.LOCAL_PLAY) ? new LocalPlayer('O', new Scanner(System.in)) : new AIPlayer('O');
        } else {
            this.playerO = new LocalPlayer('O', new Scanner(System.in));
            this.playerX = (this.mode == GameMode.LOCAL_PLAY) ? new LocalPlayer('X', new Scanner(System.in)) : new AIPlayer('X');
        }
        this.currentPlayer = this.playerX;
    }

    private void switchPlayer() {
        this.currentPlayer = (this.currentPlayer == this.playerX) ? this.playerO : this.playerX;
    }

    private boolean checkWinnerOrTie(Model model) {
        if (this.board.isWinner(this.currentPlayer.getSymbol())) {
            model.addAttribute("message", "Player " + this.currentPlayer.getSymbol() + " wins!");
            this.gameOver = true;
            return true;
        } else if (this.board.isFull()) {
            model.addAttribute("message", "The game is a tie!");
            this.gameOver = true;
            return true;
        }
        return false;
    }
}