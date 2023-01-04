package Enities.ChanceCards;

public class MoveToFerryChanceCard extends ChanceCard{
    public MoveToFerryChanceCard(String desc) {
        this.desc = desc;
    }

    @Override
    public void executeCardAction(ChanceAction chanceAction) {
        chanceAction.moveToFerry(desc);
    }
}
