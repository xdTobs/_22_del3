package Enities.Fields;

import Enities.GameBoard;

public class Ferry extends RentableField{


    public Ferry(String s) {
        super(s);
    }
    @Override
    public int getRent(int i) {
        return super.getRent(i);
    }
    @Override
    public String getName() {
        return super.getName();
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
    public Field executeFieldAction(GameBoard gameBoard) {

        return null;
    }
}
