package entities.chancecards;

public class ChangeBalConditionalChanceCard extends ChanceCard {
    int amount;
    int condition;

    public ChangeBalConditionalChanceCard(int amount, int condition, String description) {
        super(description);
        this.amount = amount;
        this.condition = condition;
    }

    @Override
    public void executeCardAction(ChanceAction chanceAction) {
        chanceAction.changeBalConditional(amount, condition);
    }
}
