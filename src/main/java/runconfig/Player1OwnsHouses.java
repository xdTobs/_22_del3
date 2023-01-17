package runconfig;

import controller.GameController;
import controller.UserIO;
import entities.GameBoard;
import entities.fields.Field;
import language.Language;
import language.LanguageHandler;
import language.Message;
import view.GuiView;

public class Player1OwnsHouses {
    public void buyHouses() {
        Field[] fields = GameBoard.getDefaultFields();
        GuiView view = GuiView.setup(fields, LanguageHandler.getDefaultLanguageHandler());
        UserIO userIO = new UserIO(view);
        String[] languages = Language.getLanguages();
        Language language = userIO.promptLanguage(languages);
        LanguageHandler languageController = LanguageHandler.getLanguageHandler(language);
        view.setLanguageController(languageController);
        int numberOfPlayers = userIO.promptRange(Message.numberOfPlayers(), 2, 4);

        GameBoard gameBoard = GameBoard.setup(fields, userIO, numberOfPlayers);

        GameController game = GameController.setup(view, userIO, gameBoard);

        game.playGame();

    }
}
