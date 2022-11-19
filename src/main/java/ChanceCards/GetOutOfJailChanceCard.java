package ChanceCards;

import Enities.GameBoard;
import Enities.Player;
import Language.LanguageHandler;

/**
 * Card to get out of jail. changes the attribute on the player of the same name to keep count
 */
public class GetOutOfJailChanceCard extends ChanceCard {


    public GetOutOfJailChanceCard() {
        this.desc = LanguageHandler.onGetOutOfJail();
    }

    @Override
    public void executeCardAction(GameBoard gameBoard) {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        currentPlayer.setGetOutOfJailCards(currentPlayer.getGetOutOfJailCards() + 1);
    }
}
