package Enities.ChanceCards;

public class ChangeBalConditionalChanceCard extends ChanceCard {
    int amount;
    int condition;

    public ChangeBalConditionalChanceCard(int amount, int condition, String desc) {
        super(desc);
        this.amount = amount;
        this.condition = condition;
    }

    @Override
    public void executeCardAction(ChanceAction chanceAction) {
        chanceAction.changeBalConditional(amount, condition);
    }
}
