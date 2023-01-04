package Enities.ChanceCards;

public class MoveSpacesChanceCard extends ChanceCard{
    int spaces;

    public MoveSpacesChanceCard(int spaces, String desc) {
        this.spaces = spaces;
        this.desc = desc;
    }

    @Override
    public void executeCardAction(ChanceAction chanceAction) {
chanceAction.moveSpaces(spaces);
        chanceAction.printDesc(desc);
    }
}
