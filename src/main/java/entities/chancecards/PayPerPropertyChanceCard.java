package entities.chancecards;

public class PayPerPropertyChanceCard extends ChanceCard {
    int perHouse;
    int perHotel;

    public PayPerPropertyChanceCard(int perHouse, int perHotel, String desc) {
        this.desc = desc;
        this.perHouse = perHouse;
        this.perHotel = perHotel;
    }

    @Override
    public void executeCardAction(ChanceAction chanceAction) {
        chanceAction.printDescription(desc);
        chanceAction.payPerProperty(perHouse, perHotel);

    }
}
