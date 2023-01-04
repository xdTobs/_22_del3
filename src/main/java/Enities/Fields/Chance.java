package Enities.Fields;

import Enities.ChanceCards.ChanceCard;
import Enities.GameBoard;

public class Chance extends Field {
    public Chance(String line) {
        super(line);
    }


    @Override
    public Field executeFieldAction(FieldAction fieldAction) {
        fieldAction.chanceAction(this);
        return null;
    }
}
