import Controller.GameHandler;
import Enities.GameBoard;
import View.*;

/**
 * The type Main.
 */
public class Main {

    /**
     * The entry point of application.
     */
    public static void main(String[] args) {
        View view = new GUI_View();
        GameBoard gameBoard = new GameBoard();

        GameHandler game = new GameHandler(view, gameBoard);
        game.playGame();
    }
}
