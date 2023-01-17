package runconfig;

import controller.GameController;
import controller.UserIO;
import entities.GameBoard;
import entities.fields.Field;
import language.LanguageController;
import language.Message;
import view.GuiView;

public class Player1OwnsHouses {
    public void buyHouses(){
        Field[] fields = GameBoard.getDefaultFields();
        GuiView view = GuiView.setup(fields, new LanguageController());
        UserIO userIO = new UserIO(view);

        int numberOfPlayers = userIO.promptRange(Message.numberOfPlayers(), 2, 4);

        GameBoard gameBoard = GameBoard.setup(fields, userIO, numberOfPlayers);

        GameController game = GameController.setup(view, userIO, gameBoard);

        game.playGame();
    }
}
