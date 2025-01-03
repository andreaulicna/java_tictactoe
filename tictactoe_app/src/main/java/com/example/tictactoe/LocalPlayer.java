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
    public char getSymbol() {
        return symbol;
    }
}