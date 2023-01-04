package Enities.ChanceCards;

public interface ChanceAction {
    void getOutOfJail(String desc);
    void changeBal(int i, String desc);
    void changeBalConditional(int amout, int condition, String desc);
    void changeBalFromPlayers(int amout, String desc);
    void moveToField(int index, String desc);
    void moveSpaces(int spaces, String desc);
    void moveToFerry(String desc);
    void payPerProperty(int perHouse, int perHotel, String desc);
    void goToJail(String desc);
    void moveToBrewery(String desc);
}
