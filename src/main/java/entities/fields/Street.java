package entities.fields;

import java.awt.*;

public class Street extends RentableField {
    Color color;

    public Street(String s, int position) {
        super(s, position);
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
