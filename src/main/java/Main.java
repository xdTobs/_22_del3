import Controller.GameHandler;
import Enities.GameBoard;
import View.*;


public class Main {

    /**
     * The entry point of application.
     */
    public static void main(String[] args) {

        GameBoard gameBoard = new GameBoard();
        View view = new GuiView(gameBoard.getFields());

        GameHandler game = new GameHandler(view, gameBoard);
        game.playGame();

    }
}
