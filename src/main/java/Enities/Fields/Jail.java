package Enities.Fields;

import Enities.GameBoard;
import Language.LanguageHandler;

public class Jail extends Field {
    private String name;
    private String description;


    public Jail() {
        super(6);
        name = LanguageHandler.getFieldName(this.getPosition());
        description = LanguageHandler.getFieldName(this.getPosition());
    }

    public String getName() {
        return name;
    }

    @Override
    public void executeFieldAction(GameBoard gameBoard) {
        return;
    }

    public String getDescription() {
        return description;
    }


}
