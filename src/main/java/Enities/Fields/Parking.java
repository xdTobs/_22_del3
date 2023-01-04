package Enities.Fields;

import Enities.GameBoard;
import Language.LanguageHandler;

public class Parking extends Field {


    public Parking(String s) {
        super(s);
    }

    public String getName() {
        return super.getName();
    }


    @Override
    public void executeFieldAction(GameBoard gameBoard) {
        // If you land on parking field nothing happens.
        return;
    }
}
