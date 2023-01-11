import controller.GameController;
import controller.UserIO;
import entities.GameBoard;
import entities.fields.Field;
import language.Message;
import view.GuiView;

public class Main {

    /**
     * The entry point of application.
     */
    public static void main(String[] args) {

        Field[] fields = GameBoard.getDefaultFields();
        GuiView view = GuiView.setup(fields);
        UserIO userIO = new UserIO(view);

        int playerCount = userIO.promptRange(Message.numberOfPlayers(), 2, 4);
        GameBoard gameBoard = GameBoard.setup(fields, userIO, playerCount);
        gameBoard.createPlayers(playerCount);

        GameController game = GameController.setup(view, userIO, gameBoard);

        game.playGame();

    }
}
