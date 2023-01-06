import Controller.GameController;
import Controller.UserIO;
import Controller.View;
import Enities.GameBoard;
import View.*;


public class Main {

    /**
     * The entry point of application.
     */
    public static void main(String[] args) {

        GameBoard gameBoard = new GameBoard();
        View view = new GuiView(gameBoard.getFields());
        UserIO userIO = null;

        GameController game = GameController.setup(view, userIO, gameBoard);

        game.playGame();

    }
}
