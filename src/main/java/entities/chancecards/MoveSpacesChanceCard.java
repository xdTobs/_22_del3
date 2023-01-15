package entities.chancecards;

public class MoveSpacesChanceCard extends ChanceCard {
    int spaces;

    public MoveSpacesChanceCard(int spaces, String desc) {
        this.spaces = spaces;
        this.desc = desc;
    }

    @Override
    public void executeCardAction(ChanceAction chanceAction) {
        chanceAction.printDescription(desc);
        chanceAction.moveSpaces(spaces);

    }
}
