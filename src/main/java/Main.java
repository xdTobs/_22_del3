//import Controller.GameController;
//import Controller.UserIO;
//import Controller.View;
//import Enities.DiceCup.DiceCup;
//import Enities.GameBoard;
//import Language.LanguageController;
//import View.*;


import Controller.GameController;
import Controller.UserIO;
import Enities.Fields.Field;
import Enities.GameBoard;
import View.GuiView;

public class Main {

    /**
     * The entry point of application.
     */
    public static void main(String[] args) {
        Field[] fields = GameBoard.getDefaultFields();
        GuiView guiView = GuiView.setup(fields);
        UserIO userIO = new UserIO(guiView);
        GameBoard gameBoard = GameBoard.setup(fields, userIO);

        GameController game = GameController.setup();

        game.playGame();

    }
}
