package Enities.ChanceCards;

public class ChangeBalChanceCard extends ChanceCard{
    public ChangeBalChanceCard(int amount,String desc) {
        this.amount = amount;
        this.desc = desc;
    }

    int amount;
    @Override
    public void executeCardAction(ChanceAction chanceAction) {
        chanceAction.changeBal(amount,desc);
    }
}
