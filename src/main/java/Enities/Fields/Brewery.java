package Enities.Fields;

import Enities.GameBoard;

public class Brewery extends RentableField {

    public Brewery(String s) {
        super(s);
    }
    @Override
    public int getPrice() {
        return super.getPrice();
    }

    @Override
    public int getHousePrice() {
        return super.getHousePrice();
    }
    @Override
    public Field executeFieldAction(FieldAction fieldAction) {
        fieldAction.breweryAction(this);
        return null;
    }

    @Override
    public int getRent(int i) {
        return super.getRent(i);
    }
    @Override
    public String getName() {
        return super.getName();
    }

}
