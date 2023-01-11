package entities.fields;

public class ChanceField extends Field {
    public ChanceField(String s, int i) {
        super(s,i);
    }

    @Override
    public String getName() {
        return super.getName();
    }


    @Override
    public Field executeFieldAction(FieldAction fieldAction) {
        fieldAction.chanceFieldAction(this);



        return null;
    }

}
