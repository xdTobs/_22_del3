package entities.fields;

import java.awt.*;

public class Street extends RentableField {
    Color color;
    int housePrice;
    int houses = 0;

    public Street(String name, int price, int housePrice, int[] rents) {
        super(name, price, rents);
        this.housePrice = housePrice;
    }

    @Override
    public int getRent(int i) {
        return rents[i];
    }

    public int getHousePrice() {
        return housePrice;
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
