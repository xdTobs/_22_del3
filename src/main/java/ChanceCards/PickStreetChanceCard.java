package ChanceCards;

import Enities.Fields.Field;
import Enities.Fields.Street;
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

    public void goToSelectedStreet(String selectedStreetName, GameBoard gameBoard) {
        for (Field street : streetsToPickFrom) {
            if (street.getName().equals(selectedStreetName)) {
                pickedStreet = gameBoard.getStreet(street.getPosition());

            }
        }
        if (pickedStreet == null) {
            throw new IllegalArgumentException("The street name was not found.");
        }
        executeCardAction(gameBoard);
        pickedStreet = null;
    }

    public String[] getStreetChoiceNames() {
        String[] choices = new String[streetsToPickFrom.length];
        for (int i = 0; i < streetsToPickFrom.length; i++) {
            choices[i] = streetsToPickFrom[i].getName();
        }
        return choices;
    }

    @Override
    public void executeCardAction(GameBoard gameBoard) {
        GotoFieldAndExecuteActionCard goToStreetAndBuyCard = new GotoFieldAndExecuteActionCard(pickedStreet);
        goToStreetAndBuyCard.executeCardAction(gameBoard);
    }
}
