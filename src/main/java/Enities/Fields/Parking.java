package Enities.Fields;

import Enities.GameBoard;
import Language.LanguageHandler;

public class Parking extends Field {
    private String name;
    private String description;


    public Parking() {
        super(12);
        name = LanguageHandler.getFieldName(this.getPosition());
        description = LanguageHandler.getFieldName(this.getPosition());
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
