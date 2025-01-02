package com.example.tictactoe;

public interface Player {
    void makeMove(Board board);
    char getSymbol();
}