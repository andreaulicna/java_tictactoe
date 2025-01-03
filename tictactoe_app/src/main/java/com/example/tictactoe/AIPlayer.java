package com.example.tictactoe;

import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AIPlayer extends AbstractPlayer {

    private static final Logger logger = LoggerFactory.getLogger(TicTacToeController.class);

    public AIPlayer(char symbol) {
        super(symbol);
    }

    private char getOpponentSymbol() {
        return (symbol == 'X') ? 'O' : 'X';
    }

    private int[] evaluateScore(Board board, int depth) {

        char winner = board.checkWinner();

        if (winner == this.symbol) {
            return new int[]{1, 10 - depth};  // Win: Highest priority
        } else if (winner == getOpponentSymbol()) {
            return new int[]{-1, depth - 10};  // Loss: Lowest priority
        } else if (board.isFull()) {
            return new int[]{0, -depth};  // Draw: Neutral priority (quicker draws over slower ones)
        }
        return null;  // Game not finished
}

    private int[] minimax(Board board, int depth, boolean isMaximizing) {
        int[] result = evaluateScore(board, depth);
        if (result != null) {
            return result;
        }

        if (isMaximizing) {
            int[] bestScore = new int[]{-2, Integer.MIN_VALUE};  // Start with worst possible score for maximizing
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board.getCharValue(row, col) == ' ') {
                        board.updateWithNewMove(row, col, this.symbol);
                        int[] score = minimax(board, depth + 1, false);
                        board.undoMove(row, col, ' ');  // Undo move
                        if (score[0] > bestScore[0] || 
                            (score[0] == bestScore[0] && score[1] > bestScore[1]) || 
                            (score[0] == 0 && bestScore[0] < 0)) {  // Prioritize avoiding losses
                            bestScore = score;
                        }
                    }
                }
            }
            return bestScore;
        } else {
            int[] bestScore = new int[]{2, Integer.MAX_VALUE};  // Start with worst possible score for minimizing
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board.getCharValue(row, col) == ' ') {
                        board.updateWithNewMove(row, col, getOpponentSymbol());
                        int[] score = minimax(board, depth + 1, true);
                        board.undoMove(row, col, ' ');  // Undo move
                        if (score[0] < bestScore[0] || 
                           (score[0] == bestScore[0] && score[1] < bestScore[1]) || 
                           (score[0] == 0 && bestScore[0] > 0)) {  // Prefer a draw to a loss
                           bestScore = score;
                        }

                    }
                }
            }
            return bestScore;
        }
        }


    public int[] calculateMove(Board board) {
        int[] bestMove = new int[2];
        int[] bestScore = new int[]{-2, Integer.MIN_VALUE};  // Initialize with the worst possible score

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board.getCharValue(row, col) == ' ') {
                    board.updateWithNewMove(row, col, this.symbol);
                    int[] score = minimax(board, 0, false);
                    board.undoMove(row, col, ' ');  // Undo move
                    //logger.info("Board state unmove:\n{}", board.toString());
                    if (score[0] > bestScore[0] || (score[0] == bestScore[0] && score[1] > bestScore[1])) {
                        bestScore = score;
                        bestMove[0] = row;
                        bestMove[1] = col;
                    }
                }
            }
        }
        return bestMove;
    }

    public void makeMove(Board board) {

        int[] move = this.calculateMove(board);

        board.updateWithNewMove(move[0], move[1], this.getSymbol());
        logger.info("Selected move: {}, {}", move[0], move[1]);
    }
}