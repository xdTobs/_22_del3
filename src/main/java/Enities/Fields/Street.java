package Enities.Fields;

import Enities.GameBoard;
import Enities.Player;

import java.awt.*;

public class Street extends RentableField {
    Color color;

    int houses = 0;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int[] getPairIndexes() {
        return pairIndexes;
    }

    public void setPairIndexes(int[] pairIndexes) {
        this.pairIndexes = pairIndexes;
    }

    public Street(String s) {
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
    public String getName() {
        return super.getName();
    }

    @Override
    public int getRent(int i) {
        return super.getRent(i);
    }

    public int getHouses() {
        return houses;
    }

    public void setHouses(int houses) {
        this.houses = houses;
    }
    @Override
    public Field executeFieldAction(FieldAction fieldAction) {
       return fieldAction.streetAction(this);
    }
}
