package com.app.tictactoe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import com.app.tictactoe.repository.MatchRepository;
import com.app.tictactoe.repository.UserRepository;

import com.app.tictactoe.game.AIPlayer;
import com.app.tictactoe.game.Board;
import com.app.tictactoe.game.Player;
import com.app.tictactoe.game.GameMode;
import com.app.tictactoe.game.GameResult;
import com.app.tictactoe.game.LocalPlayer;

import com.app.tictactoe.model.Match;
import com.app.tictactoe.model.User;

import java.util.Random;
import java.time.LocalDateTime;

@Controller
public class TicTacToeController {

    private static final Logger logger = LoggerFactory.getLogger(TicTacToeController.class);

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private UserRepository userRepository;

    private Board board = new Board();
    private Player currentPlayer;
    private Player playerX;
    private Player playerO;
    private boolean gameOver;
    private GameMode mode;

    public TicTacToeController() {
    }

    @ModelAttribute
    public void addUserDetailsToModel(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) {
            model.addAttribute("user", userDetails);
        }
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
		return "game";
	}

    @PostMapping("/move")
    public String makeMove(@RequestParam int row, @RequestParam int col, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (!this.gameOver) {
            if (this.board.updateWithNewMove(row, col, this.currentPlayer.getSymbol())) {
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

        if (this.gameOver) {
            // Save game result only if the game mode is AI_PLAY
            if (this.mode == GameMode.AI_PLAY) {
                User user = userRepository.findByUsername(userDetails.getUsername());
                GameResult result = determineResult();
                Match match = new Match(user, result, LocalDateTime.now(), mode);
                matchRepository.save(match);
            }
        }

        model.addAttribute("board", this.board);
        model.addAttribute("currentPlayer", this.currentPlayer);
        model.addAttribute("gameOver", this.gameOver);
        return "game";
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
        return "game";
    }

    private void initializePlayers() {
        Random random = new Random();
        if (random.nextBoolean()) {
            this.playerX = new LocalPlayer('X');
            this.playerO = (this.mode == GameMode.LOCAL_PLAY) ? new LocalPlayer('O') : new AIPlayer('O');
        } else {
            this.playerO = new LocalPlayer('O');
			this.playerX = (this.mode == GameMode.LOCAL_PLAY) ? new LocalPlayer('X') : new AIPlayer('X');
        }
        this.currentPlayer = this.playerX;
    }

    private void switchPlayer() {
        this.currentPlayer = (this.currentPlayer == this.playerX) ? this.playerO : this.playerX;
    }

    private boolean checkWinnerOrTie(Model model) {
        if (this.board.checkWinner() != ' ') {
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

    private GameResult determineResult() {
        char winner = this.board.checkWinner();
        if (winner == ' ') {
            return GameResult.TIE;
        } else if (this.currentPlayer instanceof AIPlayer) {
            return (winner == this.currentPlayer.getSymbol()) ? GameResult.LOSS : GameResult.WIN;
        } else {
            return (winner == this.currentPlayer.getSymbol()) ? GameResult.WIN : GameResult.LOSS;
        }
    }
}