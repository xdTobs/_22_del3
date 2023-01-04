package Enities.ChanceCards;

public class MoveToBreweryChanceCard extends ChanceCard{
    public MoveToBreweryChanceCard(String desc) {
        this.desc = desc;
    }

    @Override
    public void executeCardAction(ChanceAction chanceAction) {
    chanceAction.moveToBrewery();
    chanceAction.printDesc(desc);
    }
}
