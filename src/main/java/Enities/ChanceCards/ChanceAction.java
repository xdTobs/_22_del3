package Enities.ChanceCards;

public interface ChanceAction {
    void getOutOfJail();
    void changeBal(int i);
    void changeBalConditional(int amout, int condition);
    void changeBalFromPlayers(int amout);
    void moveToField(int index);
    void moveSpaces(int spaces);
    void moveToFerry();
    void payPerProperty(int perHouse, int perHotel);
    void goToJail();
    void moveToBrewery();
    void printDesc(String desc);
}
