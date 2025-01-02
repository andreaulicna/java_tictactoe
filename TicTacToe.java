import java.util.Random;
import java.util.Scanner;

public class TicTacToe {

    private static Board board = new Board();
    private static Player currentPlayer;
    private static Player playerX;
    private static Player playerO;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Choose game mode: 1 for LocalPlay, 2 for AIPlay");
            int mode = scanner.nextInt();
            initializePlayers(mode, scanner);

            while (true) {
                board.printBoard();
                currentPlayer.makeMove(board);
                if (board.isWinner(currentPlayer.getSymbol())) {
                    board.printBoard();
                    System.out.println("Player " + currentPlayer.getSymbol() + " wins!");
                    break;
                }
                if (board.isFull()) {
                    board.printBoard();
                    System.out.println("The game is a tie!");
                    break;
                }
                switchPlayer();
            }
        } finally {
            scanner.close();
        }
    }

    private static void initializePlayers(int mode, Scanner scanner) {
        Random random = new Random();
        if (random.nextBoolean()) {
            playerX = new LocalPlayer('X', scanner);
            playerO = (mode == 1) ? new LocalPlayer('O', scanner) : new AIPlayer('O');
        } else {
            playerO = new LocalPlayer('O', scanner);
            playerX = (mode == 1) ? new LocalPlayer('X', scanner) : new AIPlayer('X');
        }
        currentPlayer = playerX;
    }

    private static void switchPlayer() {
        currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
    }
}