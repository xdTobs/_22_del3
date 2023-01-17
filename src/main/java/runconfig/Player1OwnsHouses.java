package runconfig;

import controller.GameController;
import controller.UserIO;
import entities.GameBoard;
import entities.fields.Field;
import entities.fields.Street;
import language.LanguageController;
import language.Message;
import view.GuiView;

import java.util.List;

public class Player1OwnsHouses {

    public static void main(String[] args) {
        Field[] fields = GameBoard.getDefaultFields();
        GuiView view = GuiView.setup(fields, LanguageController.getDefaultLanguageController());
        UserIO userIO = new UserIO(view);

        int numberOfPlayers = userIO.promptRange(Message.numberOfPlayers(), 2, 4);

        GameBoard gameBoard = GameBoard.setup(fields, userIO, numberOfPlayers);

        GameController game = GameController.setup(view, userIO, gameBoard);

        Street street1 = (Street) gameBoard.getField(1);
        Street street2 = (Street) gameBoard.getField(3);
        street1.setOwner(gameBoard.getCurrentPlayer());
        street2.setOwner(gameBoard.getCurrentPlayer());
        gameBoard.getOwnershipMap().get(gameBoard.getCurrentPlayer()).addAll(List.of(street1,street2));
        game.playGame();
    }
}
