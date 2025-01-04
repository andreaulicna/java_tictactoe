package com.app.tictactoe.game;

public class Board {

    private char[][] board = new char[3][3];

    public Board() {
        initializeBoard();
    }

    public void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sb.append(board[i][j]);
                if (j < 2) sb.append(" | ");
            }
            sb.append("\n");
            if (i < 2) sb.append("---------\n");
        }
        return sb.toString();
    }

	public char checkWinner() {
    	// Check rows and columns
    	for (int i = 0; i < 3; i++) {
    	    if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
    	        return board[i][0]; // Row match
    	    }
    	    if (board[0][i] != ' ' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
    	        return board[0][i]; // Column match
    	    }
    	}

    	// Check diagonals
    	if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
    	    return board[0][0];
    	}
    	if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
    	    return board[0][2];
    	}

    	// No winner
    	return ' ';
	}


    public boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean updateWithNewMove(int row, int col, char player) {
        if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ') {
            board[row][col] = player;
            return true;
        }
        return false;
    }

	public void undoMove(int row, int col, char player) {
		board[row][col] = player;
	}
    
    public String getValue(int row, int col) {
        return String.valueOf(board[row][col]);
    }

    public char getCharValue(int row, int col) {
        return board[row][col];
    }
}