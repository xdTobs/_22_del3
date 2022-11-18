package Enities.Fields;

import Enities.GameBoard;
import Enities.Player;
import Language.LanguageHandler;

public class GoToJail extends Field {
    private String name;
    private String description;


    public GoToJail(int position) {
        super(17);
        name = LanguageHandler.getFieldName(17);
        description = "Go to jail";
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    public void executeFieldAction(GameBoard gameBoard) {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        currentPlayer.setPosition(5);
        currentPlayer.setJailed(true);
    }
}
