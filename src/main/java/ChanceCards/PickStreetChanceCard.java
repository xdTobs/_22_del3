package ChanceCards;

import Enities.Fields.Field;
import Enities.GameBoard;

/**
 * Chance card to pick a street to move to.
 * Get the street for free or pay rent when landed on.
 * We create two seperate GoToStreetCards in here and let the player select the one they prefer.
 */
public class PickStreetChanceCard extends ChanceCard {
    private Field[] streetsToPickFrom;
    private Field pickedStreet;

    public PickStreetChanceCard(Field[] streetsToPickFrom) {
        if (streetsToPickFrom.length != 2) {
            throw new IllegalArgumentException("There should be 2 gotoStreetAndBuyCards.");
        }
        this.streetsToPickFrom = streetsToPickFrom;
    }

    public void setPickedStreet(String selectedStreetName, GameBoard gameBoard) {
        for (Field street : streetsToPickFrom) {
            if (street.getName().equals(selectedStreetName)) {
                pickedStreet = gameBoard.getStreet(street.getPosition());
            }
        }
        if (pickedStreet == null) {
            throw new IllegalArgumentException("The street name was not found.");
        }
    }

    public String[] getStreetChoiceNames() {
        String[] choices = new String[streetsToPickFrom.length];
        for (int i = 0; i < streetsToPickFrom.length; i++) {
            choices[i] = streetsToPickFrom[i].getName();
        }
        return choices;
    }

    // Once the card has been executed it goes back to the bottom of the deck.
    // We don't have to set the picked street to null, because the card the player will be prompted again when this card comes up again.
    // But we do that anyway for future proofing.
    @Override
    public void executeCardAction(GameBoard gameBoard) {
        GotoFieldAndExecuteActionCard goToStreetAndBuyCard = new GotoFieldAndExecuteActionCard(pickedStreet);
        goToStreetAndBuyCard.executeCardAction(gameBoard);
        pickedStreet = null;
    }
}
