package Enities.Fields;

import Enities.GameBoard;

public class Jail extends Field {



    public Jail(String s) {
        super(s);
    }

    public String getName() {
        return super.getName();
    }

    @Override
    public Field executeFieldAction(FieldAction fieldAction) {

        return null;
    }
    public int[] getPairIndexes() {
        return pairIndexes;
    }

    public void setPairIndexes(int[] pairIndexes) {
        this.pairIndexes = pairIndexes;
    }
}
