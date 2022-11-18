package Enities.Fields;

import Enities.GameBoard;
import Enities.Player;
import Language.LanguageHandler;

public class GoToJail extends Field {
    private String name;
    private String description;


    public GoToJail(int position) {
        super(18);
        name = LanguageHandler.getFieldName(this.getPosition());
        description = LanguageHandler.getFieldName(this.getPosition());
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    public void executeFieldAction(GameBoard gameBoard) {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        currentPlayer.setPosition(this.getPosition());
        currentPlayer.setJailed(true);
    }
}
