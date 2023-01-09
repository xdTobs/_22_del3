package entities.chancecards;

public interface ChanceAction {
    void getOutOfJail();
    void changeBal(int i);
    void changeBalConditional(int amount, int condition);
    void changeBalFromPlayers(int amount);
    void moveToField(int index);
    void moveSpaces(int spaces);
    void moveToFerry();
    void payPerProperty(int perHouse, int perHotel);
    void goToJail();
    void moveToBrewery();
    void printDescription(String desc);
}
