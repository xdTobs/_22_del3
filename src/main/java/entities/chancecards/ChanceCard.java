package entities.chancecards;

public abstract class ChanceCard {

    String description;

    public ChanceCard(String description) {
        this.description = description;
    }

    public void executeCardAction(ChanceAction chanceAction) {
        chanceAction.printDescription(description);
    }

}


