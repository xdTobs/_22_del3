package runconfig;

import controller.GameController;
import controller.UserIO;
import entities.GameBoard;
import entities.fields.Ferry;
import entities.fields.Field;
import language.LanguageController;
import language.Message;
import view.GuiView;

import java.util.List;

public class Player1OwnsBreweries {
    public static void main(String[] args) {
        Field[] fields = GameBoard.getDefaultFields();
        GuiView view = GuiView.setup(fields, LanguageController.getDefaultLanguageController());
        UserIO userIO = new UserIO(view);

        int numberOfPlayers = userIO.promptRange(Message.numberOfPlayers(), 2, 4);

        GameBoard gameBoard = GameBoard.setup(fields, userIO, numberOfPlayers);

        GameController game = GameController.setup(view, userIO, gameBoard);

        Ferry ferry1 = (Ferry) gameBoard.getField(5);
        Ferry ferry2 = (Ferry) gameBoard.getField(15);
        Ferry ferry3 = (Ferry) gameBoard.getField(25);
        Ferry ferry4 = (Ferry) gameBoard.getField(35);
        ferry1.setOwner(gameBoard.getCurrentPlayer());
        ferry2.setOwner(gameBoard.getCurrentPlayer());
        ferry3.setOwner(gameBoard.getCurrentPlayer());
        ferry4.setOwner(gameBoard.getCurrentPlayer());
        gameBoard.getOwnershipMap().get(gameBoard.getCurrentPlayer()).addAll(List.of(ferry1,ferry2,ferry3,ferry4));
        game.playGame();
    }
}
