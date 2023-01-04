package Enities.Fields;

import Enities.GameBoard;
import Enities.Player;
import Language.LanguageHandler;

public class GoToJail extends Field {

    public GoToJail(String s) {
        super(s);
    }

    public void executeFieldAction(GameBoard gameBoard) {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        currentPlayer.setPosition(10);
        currentPlayer.setJailed(true);
    }
}
