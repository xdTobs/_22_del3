package entities.chancecards;

public class MoveToFerryChanceCard extends ChanceCard{
    public MoveToFerryChanceCard(String description) {
        super(description);
    }

    @Override
    public void executeCardAction(ChanceAction chanceAction) {
        chanceAction.moveToFerry();
        super.executeCardAction(chanceAction);
    }
}
