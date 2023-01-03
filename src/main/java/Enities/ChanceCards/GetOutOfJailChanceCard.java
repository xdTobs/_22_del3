package Enities.ChanceCards;

import Enities.GameBoard;
import Enities.Player;
import Language.LanguageHandler;

/**
 * Card to get out of jail. changes the attribute on the player of the same name to keep count
 */
public class GetOutOfJailChanceCard extends ChanceCard {


    public GetOutOfJailChanceCard() {
        this.desc = language.languageMap.get("onGetOutOfJail");
    }

    @Override
    public void executeCardAction(GameBoard gameBoard) {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        currentPlayer.addGetOutOfJailCard();
    }

    @Override
    public void executeCardAction(ChanceAction chanceAction) {
        chanceAction.getOutOfJail();
    }
}
