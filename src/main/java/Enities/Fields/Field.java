package Enities.Fields;

import Enities.GameBoard;

public abstract class Field {
    private int position;

    public Field(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public abstract String getName();

    public abstract String getDescription();
    public abstract void executeFieldAction(GameBoard gameBoard);
}
