package Enities.Fields;

import Enities.GameBoard;

public class Jail extends Field {



    public Jail(String s) {
        super(s);
    }

    public String getName() {
        return super.getName();
    }

    @Override
    public Field executeFieldAction(GameBoard gameBoard) {
        return null;
    }
}
