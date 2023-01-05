package Enities.Fields;

public class GoToJail extends Field {

    public GoToJail(String s) {
        super(s);
    }

    public Field executeFieldAction(FieldAction fieldAction) {
        fieldAction.goToJailAction(this);
        return null;
    }


}
