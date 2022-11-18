package Enities.Fields;

import Enities.GameBoard;
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

    @Override
    public void executeFieldAction(GameBoard gameBoard) {
        // If you land on parking field nothing happens.
        return;
    }
}
