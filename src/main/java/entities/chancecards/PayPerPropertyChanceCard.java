package entities.chancecards;

public class PayPerPropertyChanceCard extends ChanceCard{
    int perHouse;
    int perHotel;

    public PayPerPropertyChanceCard(int perHouse, int perHotel,String description) {
        super(description);
        this.perHouse = perHouse;
        this.perHotel = perHotel;
    }

    @Override
    public void executeCardAction(ChanceAction chanceAction) {
        super.executeCardAction(chanceAction);
        chanceAction.payPerProperty(perHouse,perHotel);
    }
}
