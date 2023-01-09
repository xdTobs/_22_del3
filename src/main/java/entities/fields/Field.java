package entities.fields;

public abstract class Field {
    protected FieldPair pair;
    private int position;
    private String name;

    public Field(String s) {
        this.parse(s);
    }

    public FieldPair getPair() {
        return pair;
    }

    public void setPair(FieldPair pair) {
        this.pair = pair;
    }

    public int getPosition() {
        return position;
    }

    protected void parse(String s) {
        String[] split = s.split(",");
        this.position = Integer.parseInt(split[1]);
        this.name = split[0];
    }

    public abstract Field executeFieldAction(FieldAction fieldAction);

    public String getName() {
        return name;
    }

}
