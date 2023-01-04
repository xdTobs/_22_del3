package Enities.ChanceCards;

public class ChangeBalConditionalChanceCard extends ChanceCard{
    int amount;
    int condition;

    public ChangeBalConditionalChanceCard(int amount, int condition,String desc) {
        this.amount = amount;
        this.condition = condition;
        this.desc = desc;
    }

    @Override
    public void executeCardAction(ChanceAction chanceAction) {
        chanceAction.changeBalConditional(amount,condition,desc);
    }
}
