import controller.GameController;
import controller.UserIO;
import entities.GameBoard;
import entities.fields.Field;
import language.Language;
import language.LanguageHandler;
import language.Message;
import view.GuiView;

public class Main {

    /**
     * The entry point of application.
     */
    public static void main(String[] args) {
        // We use the try catch block to make sure that the gui quits if an exception is thrown.
        // Without this the gui keeps running in a bugged state.
        try {
            Field[] fields = GameBoard.getDefaultFields();
            GuiView view = GuiView.setup(fields, LanguageHandler.getDefaultLanguageHandler());
            UserIO userIO = new UserIO(view);
            Language language = userIO.promptLanguage(Language.getLanguages());
            LanguageHandler languageHandler = LanguageHandler.getLanguageHandler(language);
            view.setLanguageHandler(languageHandler);
            int numberOfPlayers = userIO.promptRange(Message.numberOfPlayers(), 2, 4);
            GameBoard gameBoard = GameBoard.setup(fields, userIO, numberOfPlayers);
            GameController game = GameController.setup(view, userIO, gameBoard);
            game.playGame();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
