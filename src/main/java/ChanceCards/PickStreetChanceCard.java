package ChanceCards;

import Enities.GameBoard;
import Language.LanguageHandler;
import View.GUI_View;

/**
 * Chance card to pick a street to move to.
 * Get the street for free or pay rent when landed on.
 * We create two seperate GoToStreetCards in here and let the player select the one they prefer.
 */
public class PickStreetChanceCard extends ChanceCard {
    private GotoStreetAndBuyCard[] gotoStreetCards;
    private GotoStreetAndBuyCard selectedCard = null;

    public PickStreetChanceCard(GotoStreetAndBuyCard[] gotoStreetAndBuyCards) {
        if (gotoStreetAndBuyCards.length != 2) {
            throw new IllegalArgumentException("There should be 2 gotoStreetAndBuyCards.");
        }
        this.gotoStreetCards = gotoStreetAndBuyCards;
    }

    public void promptPlayerForStreet(GUI_View view) {
        String message = LanguageHandler.chanceCardMsg() + " " + LanguageHandler.onPickFieldChance();
        String[] choices = getStreetChoiceNames();
        String answer = view.promptPlayer(choices, message);
        for (GotoStreetAndBuyCard gotoStreetCard :
                gotoStreetCards) {
            if (gotoStreetCard.getStreetName().equals(answer)) {
                selectedCard = gotoStreetCard;
            }
        }
    }

    public void executeCardAction(GameBoard gameBoard) {
        selectedCard.executeCardAction(gameBoard);
        selectedCard = null;
    }

    public String[] getStreetChoiceNames() {
        String[] choices = new String[gotoStreetCards.length];
        for (int i = 0; i < gotoStreetCards.length; i++) {
            choices[i] = gotoStreetCards[i].getStreetName();
        }
        return choices;
    }
}
