package entities.fields;

public class Jail extends Field {


    public Jail(String s) {
        super(s);
    }

    @Override
    public Field executeFieldAction(FieldAction fieldAction) {
        fieldAction.jailAction(this);
        return null;
    }
}
