package Enities.Fields;

import Language.LanguageHandler;

public class Parking  extends Field{
    private String name;
    private String description;


    public Parking() {
        super(11);
        name = LanguageHandler.getFieldName(11);
        description = "Parking is here.";
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
