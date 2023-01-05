package Enities.Fields;

public abstract class Field {
    private int position;
    private String name;
    protected FieldPair pair;

    public FieldPair getPair() {
        return pair;
    }

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

    public abstract Field executeFieldAction(FieldAction fieldAction);

    public String getName() {
        return name;
    }

    public String getHTMLDescription() {
        return "<span style='background-color: red'>" + this.getName() + "</span>";
    }

    public void setPair(FieldPair pair) {
        this.pair = pair;
    }
}
