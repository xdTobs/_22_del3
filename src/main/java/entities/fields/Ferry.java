package entities.fields;

public class Ferry extends RentableField{


    public Ferry(String s) {
        super(s);
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
    public Field executeFieldAction(FieldAction fieldAction) {
        fieldAction.ferryAction(this);
        return null;
    }

}
