package entities.chancecards;

public class ChangeBalFromPlayersChanceCard extends ChanceCard {
    int amount;


    public ChangeBalFromPlayersChanceCard(int amount, String description) {
        super(description);
        this.amount = amount;
    }

    @Override
    public void executeCardAction(ChanceAction chanceAction) {
        chanceAction.changeBalFromPlayers(amount);
        super.executeCardAction(chanceAction);
    }
}
