package entities.chancecards;

public class ChangeBalFromPlayersChanceCard extends ChanceCard {
    int amount;


    public ChangeBalFromPlayersChanceCard(int amount, String desc) {
        this.amount = amount;
        this.desc = desc;
    }

    @Override
    public void executeCardAction(ChanceAction chanceAction) {
        chanceAction.printDescription(desc);
        chanceAction.changeBalFromPlayers(amount);

    }
}
