package Enities.Fields;

import Language.LanguageHandler;

public class Jail extends Field {
    private String name;
    private String description;


    public Jail() {
        super(5);
        name = LanguageHandler.getFieldName(5);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
