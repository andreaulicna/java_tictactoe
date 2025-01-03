package com.example.tictactoe;

import java.util.Random;

public class AIPlayer implements Player {
    private char symbol;

    public AIPlayer(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    public int[] calculateMove(Board board) {
        // AI logic TBA
        // For now, just calculate a random move
        int [] move = new int[2];
        Random random = new Random();

        move[0] = random.nextInt(3);
        move[1] = random.nextInt(3);
        return move;
    }

    public void makeMove(Board board) {
		int[] move = this.calculateMove(board);

		while (!board.makeMove(move[0], move[1], this.getSymbol())) {
			move = this.calculateMove(board);
		}
    }
}