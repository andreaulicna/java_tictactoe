package com.example.tictactoe;

public class AIPlayer implements Player {
    private char symbol;

    public AIPlayer(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public void makeMove(Board board) {
        // AI logic TBA
        // For now, just make a random move
        System.out.println("AI Player " + symbol + " making a move: ");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.makeMove(i, j, symbol)) {
                    return;
                }
            }
        }
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}