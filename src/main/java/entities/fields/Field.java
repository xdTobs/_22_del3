package entities.fields;


public abstract class Field {
    private FieldPair pair = null;
    private final String name;

    // todo ska field ha styr pa position. NEJ.
    protected Field(String name) {
        this.name = name;
    }

    public FieldPair getPair() {
        return pair;
    }

    public void setPair(FieldPair pair) {
        this.pair = pair;
    }

    public static Field parse(String line) {
        String[] key = line.split(",");
        String name = key[0];
        return switch (key[2].trim()) {
            case ("street") ->
                    new Street(name, Integer.parseInt(key[3]), Integer.parseInt(key[4]), RentableField.parseRent(line));
            case ("tax") -> new Tax(name, 10, 4000);
            case ("jail") -> new Jail(name);
            case ("goToJail") -> new GoToJail(name);
            case ("chance") -> new ChanceField(name);
            case ("refugee") -> new Parking(name);
            case ("start") -> new Start(name);
            case ("brewery") -> new Brewery(name, Integer.parseInt(key[3]), RentableField.parseRent(line));
            case ("ferry") -> new Ferry(name, Integer.parseInt(key[3]), RentableField.parseRent(line));
            default -> throw new IllegalStateException("Unexpected value while parsing line for field: " + key[2].trim()
                    + " in line: " + line);
        };

    }

    public abstract Field executeFieldAction(FieldAction fieldAction);

    public String getName() {
        return name;
    }
}
