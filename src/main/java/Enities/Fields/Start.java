package Enities.Fields;

public class Start  extends Field{
    private String name;
    private String description;


    public Start(int position) {
        super(0);
        name="Start";
        description="This is start";
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
