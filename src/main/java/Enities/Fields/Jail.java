package Enities.Fields;

import Enities.GameBoard;
import Language.LanguageHandler;

public class Jail extends Field {



    public Jail(String s) {
        super(s);
    }

    public String getName() {
        return super.getName();
    }

    @Override
    public void executeFieldAction(GameBoard gameBoard) {
        return;
    }
}
