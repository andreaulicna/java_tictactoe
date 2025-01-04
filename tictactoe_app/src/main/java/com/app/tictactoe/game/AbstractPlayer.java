package tictactoe_app.src.main.java.com.app.tictactoe.game;

public abstract class AbstractPlayer implements Player {
    protected char symbol;

    public AbstractPlayer(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}