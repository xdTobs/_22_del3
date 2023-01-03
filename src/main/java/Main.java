import Controller.GameHandler;
import Enities.Fields.Field;
import Enities.GameBoard;
import Language.LanguageHandler;
import View.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Main {

    /**
     * The entry point of application.
     */
    public static void main(String[] args) throws IOException {
        GameBoard gameBoard = new GameBoard();
        GUI_View view = new GUI_View(gameBoard.getFields());

        GameHandler game = new GameHandler(view, gameBoard);
        game.playGame();

    }
}
