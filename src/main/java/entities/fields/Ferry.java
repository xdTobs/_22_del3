package entities.fields;

public class Ferry extends RentableField {


    public Ferry(String name, int price, int[] rents) {
        super(name, price, rents);
    }


    @Override
    public Field executeFieldAction(FieldAction fieldAction) {
        fieldAction.ferryAction(this);
        return null;
    }

}
