package Enities.ChanceCards;

import Enities.GameBoard;
import Language.LanguageHandler;

import java.io.IOException;

public abstract class ChanceCard {
    protected LanguageHandler language;

    {
        try {
            language = new LanguageHandler();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected String desc;

    public abstract void executeCardAction(ChanceAction chanceAction);

}


