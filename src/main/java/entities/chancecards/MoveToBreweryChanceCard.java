package entities.chancecards;

public class MoveToBreweryChanceCard extends ChanceCard{
    public MoveToBreweryChanceCard(String description) {
        super(description);
    }

    @Override
    public void executeCardAction(ChanceAction chanceAction) {
    chanceAction.moveToBrewery();
    super.executeCardAction(chanceAction);
    }
}
