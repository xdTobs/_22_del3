package Enities.Fields;

import Enities.GameBoard;

public interface FieldAction {
    Field streetAction(Street street);
    RentableField buyEmptyStreet(Street street);
    void streetPayRentToOwner(Street street);
    boolean streetPlayerOwnsPair(Street street);
    void taxAction(Tax tax);
    void goToJailAction(GoToJail goToJail);

    Field ferryAction(Ferry ferry);

    void chanceFieldAction(ChanceField chanceField);

    Field breweryAction(Brewery brewery);
}
