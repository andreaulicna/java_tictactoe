package com.example.tictactoe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;
import java.util.Scanner;

@Controller
public class TicTacToeController {

    private static final Logger logger = LoggerFactory.getLogger(TicTacToeController.class);

    private Board board = new Board();
    private Player currentPlayer;
    private Player playerX;
    private Player playerO;

    
    public TicTacToeController() {
        initializePlayers(1); // Default to LocalPlay for now
    }

    @GetMapping("/")
    public String index(Model model) {
        logger.info("Board state: {}", this.board);
        logger.info("Value at (0,0): {}", this.board.getValue(0, 0));
        model.addAttribute("board", this.board);
        model.addAttribute("currentPlayer", this.currentPlayer.getSymbol());
        return "index";
    }

    @PostMapping("/move")
    public String makeMove(@RequestParam int row, @RequestParam int col, Model model) {
        if (this.board.makeMove(row, col, this.currentPlayer.getSymbol())) {
            if (this.board.isWinner(this.currentPlayer.getSymbol())) {
                model.addAttribute("message", "Player " + this.currentPlayer.getSymbol() + " wins!");
            } else if (this.board.isFull()) {
                model.addAttribute("message", "The game is a tie!");
            } else {
                switchPlayer();
            }
        } else {
            model.addAttribute("message", "This move is not valid");
        }
        model.addAttribute("board", this.board);
        model.addAttribute("currentPlayer", this.currentPlayer.getSymbol());
        return "index";
    }

    private void initializePlayers(int mode) {
        Random random = new Random();
        if (random.nextBoolean()) {
            this.playerX = new LocalPlayer('X', new Scanner(System.in));
            this.playerO = (mode == 1) ? new LocalPlayer('O', new Scanner(System.in)) : new AIPlayer('O');
        } else {
            this.playerO = new LocalPlayer('O', new Scanner(System.in));
            this.playerX = (mode == 1) ? new LocalPlayer('X', new Scanner(System.in)) : new AIPlayer('X');
        }
        this.currentPlayer = this.playerX;
    }

    private void switchPlayer() {
        this.currentPlayer = (this.currentPlayer == this.playerX) ? this.playerO : this.playerX;
    }
}