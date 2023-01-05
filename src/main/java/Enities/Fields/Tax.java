package Enities.Fields;

import Enities.GameBoard;

public class Tax extends Field {
    int price;
    int percentPrice;
    public Tax(String s) {
        super(s);
        price = 2000;
        percentPrice = 10;
        //TODO actual values here
    }

    @Override
    public Field executeFieldAction(FieldAction fieldAction) {
        fieldAction.taxAction(this);
        return null;
    }

    public int[] getPairIndexes() {
        return pairIndexes;
    }

    public void setPairIndexes(int[] pairIndexes) {
        this.pairIndexes = pairIndexes;
    }
}
