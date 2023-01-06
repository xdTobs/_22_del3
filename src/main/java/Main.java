import Controller.GameController;
import Controller.UserIO;
import Controller.View;
import Enities.DiceCup.DiceCup;
import Enities.GameBoard;
import Language.LanguageController;
import View.*;


public class Main {

    /**
     * The entry point of application.
     */
    public static void main(String[] args) {

        GameBoard gameBoard = GameBoard.setup(new LanguageController(), new DiceCup(), "fields.csv");
        View view = new GuiView(gameBoard.getFields());
        UserIO userIO = null;

        GameController game = GameController.setup(view, userIO, gameBoard);

        game.playGame();

    }
}
