package entities.chancecards;

public class MoveSpacesChanceCard extends ChanceCard {
    int spaces;

    public MoveSpacesChanceCard(int spaces, String description) {
        super(description);
        this.spaces = spaces;
    }

    @Override
    public void executeCardAction(ChanceAction chanceAction) {
        chanceAction.moveSpaces(spaces);
        super.executeCardAction(chanceAction);
    }
}
