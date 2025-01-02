package com.example.tictactoe;

import java.util.Scanner;

public class LocalPlayer implements Player {
    private char symbol;
    private Scanner scanner;

    public LocalPlayer(char symbol, Scanner scanner) {
        this.symbol = symbol;
        this.scanner = scanner;
    }

    @Override
    public void makeMove(Board board) {
        int row, col;
        while (true) {
            System.out.println("Player " + symbol + ", enter your move (row and column): ");
            row = scanner.nextInt();
            col = scanner.nextInt();
            if (board.makeMove(row, col, symbol)) {
                break;
            } else {
                System.out.println("This move is not valid");
            }
        }
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}