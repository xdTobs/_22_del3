package entities.chancecards;

/**
 * Card to get out of jail. changes the attribute on the player of the same name to keep count
 */
public class GetOutOfJailChanceCard extends ChanceCard {

    public GetOutOfJailChanceCard(String description) {
        super(description);
    }

    @Override
    public void executeCardAction(ChanceAction chanceAction) {
        chanceAction.getOutOfJail();
        super.executeCardAction(chanceAction);
    }
}
