import Controller.GameController;
import Enities.GameBoard;
import View.*;

public class Main {

    /**
     * The entry point of application.
     */
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard();
        GUI_View view = new GUI_View(gameBoard.getFields());

        GameController game = new GameController(view, gameBoard);
        game.playGame();
    }
}
