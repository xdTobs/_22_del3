package Enities.Fields;

import Enities.GameBoard;
import Language.LanguageHandler;

public class Chance extends Field {
    private String name;
    private String description;


    public Chance(int position) {
        super(position);
        name = LanguageHandler.getFieldName(position);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void executeFieldAction(GameBoard gameBoard) {

//        TODO
    }

}
