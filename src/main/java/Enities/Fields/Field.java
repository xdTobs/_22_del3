package Enities.Fields;

import Enities.GameBoard;

public abstract class Field {
    private int position;
    private String name;
    protected int[] pairIndexes;

    public int[] getPairIndexes() {
        return pairIndexes;
    }

    public void setPairIndexes(int[] pairIndexes) {
        this.pairIndexes = pairIndexes;
    }

    public Field(int position) {
        this.position = position;
    }
    public Field(String s) {

        this.parse(s);
    }
    public int getPosition() {
        return position;
    }

    protected void parse(String s){
        String[] split = s.split(",");
        this.position = Integer.parseInt(split[1]);
        this.name = split[0];
    }

    public abstract Field executeFieldAction(FieldAction fieldAction);

    public String getName() {
        return name;
    }
}
