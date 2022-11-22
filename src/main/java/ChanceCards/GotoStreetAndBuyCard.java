package ChanceCards;

import Enities.Fields.Street;
import Language.LanguageHandler;
import Enities.*;

/**
 * Chance card to move to a space.
 * Get the street for free or pay rent when landed on.
 */
public class GotoStreetAndBuyCard extends ChanceCard {
    final private Street street;

    public GotoStreetAndBuyCard(Street street) {
        this.street = street;
        this.desc = LanguageHandler.moveTo() + " " + LanguageHandler.getFieldName(street.getPosition());
    }

    @Override
    public void executeCardAction(GameBoard gameBoard) {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        Player[] players = gameBoard.getPlayers();
        currentPlayer.setPosition(street.getPosition());
        if (street.getOwnerName().equals("Bank")) {
            currentPlayer.addBalance(-street.getRent());
            street.setOwnerName(currentPlayer.getName());
        } else {
            Player owner = null;
            for (Player p : players) {
                if (p.getName().equals(street.getOwnerName())) {
                    owner = p;
                }
            }
            if (owner != null) {
                currentPlayer.addBalance(-street.getRent());
                owner.addBalance(street.getRent());
            } else {
                throw new NullPointerException("Owner of street is null");
            }
        }
    }
}
