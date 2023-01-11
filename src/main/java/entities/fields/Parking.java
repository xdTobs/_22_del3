package entities.fields;

public class Parking extends Field {


    public Parking(String s) {
        super(s);
    }


    @Override
    public Field executeFieldAction(FieldAction fieldAction) {
        // If you land on parking field nothing happens.
        return null;
    }

}
