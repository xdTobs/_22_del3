package entities.fields;

public class ChanceField extends Field {
    public ChanceField(String name) {
        super(name);
    }


    @Override
    public Field executeFieldAction(FieldAction fieldAction) {
        fieldAction.chanceFieldAction(this);



        return null;
    }

}
