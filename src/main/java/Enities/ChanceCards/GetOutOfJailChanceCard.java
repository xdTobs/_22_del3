package Enities.ChanceCards;

/**
 * Card to get out of jail. changes the attribute on the player of the same name to keep count
 */
public class GetOutOfJailChanceCard extends ChanceCard {

    public GetOutOfJailChanceCard(String desc) {
        this.desc = desc;
    }

    @Override
    public void executeCardAction(ChanceAction chanceAction) {
        chanceAction.getOutOfJail();
        chanceAction.printDesc(desc);
    }
}
