package Enities.Fields;

public class Jail extends Field {


    public Jail(String s) {
        super(s);
    }

    public String getName() {
        return super.getName();
    }

    @Override
    public Field executeFieldAction(FieldAction fieldAction) {
        fieldAction.jailAction(this);
        return null;
    }



}
