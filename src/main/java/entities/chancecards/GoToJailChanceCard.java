package entities.chancecards;

public class GoToJailChanceCard extends ChanceCard{
    public GoToJailChanceCard(String description) {
        super(description);
    }

    @Override
    public void executeCardAction(ChanceAction chanceAction) {
        chanceAction.goToJail();
        super.executeCardAction(chanceAction);
    }
}
