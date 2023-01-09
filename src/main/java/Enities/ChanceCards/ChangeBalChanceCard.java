package Enities.ChanceCards;

public class ChangeBalChanceCard extends ChanceCard {
    public ChangeBalChanceCard(int amount, String desc) {
        this.desc = desc;
        this.amount = amount;
    }

    int amount;

    @Override
    public void executeCardAction(ChanceAction chanceAction) {
        chanceAction.changeBal(amount);
        chanceAction.printDescription(desc);
    }
}
