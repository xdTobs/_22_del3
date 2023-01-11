package entities.fields;

public class Parking extends Field {


    public Parking(String s) {
        super(s);
    }

    public String getName() {
        return super.getName();
    }


    @Override
    public Field executeFieldAction(FieldAction fieldAction) {
        // If you land on parking field nothing happens.
        fieldAction.parkingAction();
        return null;
    }

}
