package entities.chancecards;

public class GoToJailChanceCard extends ChanceCard{
    public GoToJailChanceCard(String desc) {
        this.desc = desc;
    }

    @Override
    public void executeCardAction(ChanceAction chanceAction) {
        chanceAction.printDescription(desc);
        chanceAction.goToJail();
    }
}
