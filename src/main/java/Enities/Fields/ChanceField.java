package Enities.Fields;

import Enities.ChanceCards.ChanceCard;
import Enities.GameBoard;

public class ChanceField extends Field {
    public ChanceField(String s) {
        super(s);
    }

    @Override
    public String getName() {
        return super.getName();
    }


    @Override
    public Field executeFieldAction(FieldAction fieldAction) {
        fieldAction.chanceFieldAction(this);



        return null;
    }

}
