import controller.GameController;
import controller.UserIO;
import entities.GameBoard;
import entities.fields.Field;
import language.Language;
import language.LanguageController;
import language.Message;
import view.GuiView;

public class Main {

    /**
     * The entry point of application.
     */
    public static void main(String[] args) {

        Field[] fields = GameBoard.getDefaultFields();
        GuiView view = GuiView.setup(fields, LanguageController.getDefaultLanguageController());
        UserIO userIO = new UserIO(view);
        String[] languages = Language.getLanguages();
        Language language = userIO.promptLanguage(languages);
        LanguageController languageController = LanguageController.getLanguageController(language);
        view.setLanguageController(languageController);
        int numberOfPlayers = userIO.promptRange(Message.numberOfPlayers(), 2, 4);

        GameBoard gameBoard = GameBoard.setup(fields, userIO, numberOfPlayers);

        GameController game = GameController.setup(view, userIO, gameBoard);

        game.playGame();

    }
}
//tilføj et Grid pane? Layout, visuelt
//