package entities.fields;

public interface FieldAction {
    Field streetAction(Street street);
    void streetPayRentToOwner(Street street);
    boolean streetPlayerOwnsPair(Street street);
    void taxAction(Tax tax);
    void goToJailAction(GoToJail goToJail);

    void jailAction(Jail jail);

    Field ferryAction(Ferry ferry);

    void chanceFieldAction(ChanceField chanceField);

    Field breweryAction(Brewery brewery);
}
