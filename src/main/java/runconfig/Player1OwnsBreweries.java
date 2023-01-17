package runconfig;

import controller.GameController;
import controller.UserIO;
import entities.GameBoard;
import entities.fields.Brewery;
import entities.fields.Ferry;
import entities.fields.Field;
import language.LanguageHandler;
import language.Message;
import view.GuiView;

import java.util.List;

public class Player1OwnsBreweries {
    public static void main(String[] args) {
        Field[] fields = GameBoard.getDefaultFields();
        GuiView view = GuiView.setup(fields, LanguageHandler.getDefaultLanguageHandler());
        UserIO userIO = new UserIO(view);

        int numberOfPlayers = userIO.promptRange(Message.numberOfPlayers(), 2, 4);

        GameBoard gameBoard = GameBoard.setup(fields, userIO, numberOfPlayers);

        GameController game = GameController.setup(view, userIO, gameBoard);

        Brewery brewery1 = (Brewery) gameBoard.getField(12);
        Brewery brewery2 = (Brewery) gameBoard.getField(28);
        brewery1.setOwner(gameBoard.getCurrentPlayer());
        brewery2.setOwner(gameBoard.getCurrentPlayer());
        gameBoard.getOwnershipMap().get(gameBoard.getCurrentPlayer()).addAll(List.of(brewery1,brewery2));
        game.playGame();
    }
}
