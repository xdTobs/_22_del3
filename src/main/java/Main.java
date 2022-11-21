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
        GameBoard gameBoard = new GameBoard();
        GUI_View view = new GUI_View(gameBoard.getPlayers(), gameBoard.getFields());

        GameHandler game = new GameHandler(view, gameBoard);
        game.playGame();
    }
}
