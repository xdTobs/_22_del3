package entities.fields;

public abstract class Field {
    private FieldPair pair = null;
    private final int position;
    private final String name;

    // todo ska field ha styr pa position. NEJ.
    protected Field(String name, int position) {
        this.position = position;
        this.name = name;
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

    public static Field parse(String line) {
        String[] key = line.split(",");
        int position = Integer.parseInt(key[1]);
        String name = key[0];
        return switch (key[2].trim()) {
            case ("street") -> new Street(name, position);
            case ("tax") -> new Tax(name, position);
            case ("jail") -> new Jail(name, position);
            case ("gotoJail") -> new GoToJail(name, position);
            case ("chance") -> new ChanceField(name, position);
            case ("refugee") -> new Parking(name, position);
            case ("start") -> new Start(name, position);
            case ("brewery") -> new Brewery(name, position);
            case ("ferry") -> new Ferry(name, position);
            default -> throw new IllegalStateException("Unexpected value: " + key[2].trim());
        };

    }

    public abstract Field executeFieldAction(FieldAction fieldAction);

    public String getName() {
        return name;
    }

    public String getHTMLDescription() {
        return "<span style='background-color: red'>" + this.getName() + "</span>";
    }
}
