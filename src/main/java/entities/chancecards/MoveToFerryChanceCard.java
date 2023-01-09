package entities.chancecards;

public class MoveToFerryChanceCard extends ChanceCard{
    public MoveToFerryChanceCard(String desc) {
        this.desc = desc;
    }

    @Override
    public void executeCardAction(ChanceAction chanceAction) {
        chanceAction.moveToFerry();
        chanceAction.printDescription(desc);
    }
}
