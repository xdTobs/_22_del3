package entities.chancecards;

public class MoveToBreweryChanceCard extends ChanceCard{
    public MoveToBreweryChanceCard(String desc) {
        this.desc = desc;
    }

    @Override
    public void executeCardAction(ChanceAction chanceAction) {
        chanceAction.printDescription(desc);
        chanceAction.moveToBrewery();

    }
}
