package Enities.Fields;

import Enities.GameBoard;

import java.util.ArrayList;

public abstract class Field {
    private int position;
    private String name;

    public Field(String s) {
        this.parse(s);
    }

    public int getPosition() {
        return position;
    }

    protected void parse(String s) {
        String[] split = s.split(",");
        this.position = Integer.parseInt(split[1]);
        this.name = split[0];
    }

    public abstract void executeFieldAction(GameBoard gameBoard);

    public String getName() {
        return name;
    }
}
