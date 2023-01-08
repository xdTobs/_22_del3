package Enities.ChanceCards;

import java.io.IOException;

public abstract class ChanceCard {

    protected String desc;

    public ChanceCard(String desc) {
        this.desc = desc;
    }

    public abstract void executeCardAction(ChanceAction chanceAction);

}


