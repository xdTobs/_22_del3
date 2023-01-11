package entities.fields;

public class Brewery extends RentableField {

    public Brewery(String name, int price, int[] rents) {
        super(name, price, rents);
    }


    @Override
    public int getRent(int i) {
        return rents[i];
    }

    @Override
    public Field executeFieldAction(FieldAction fieldAction) {
        fieldAction.breweryAction(this);
        return null;
    }
}
