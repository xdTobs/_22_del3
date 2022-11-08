import gui_fields.GUI_Car;

public class Player {
    private String name;
    private int balance;
    private GUI_Car car;
    private int id;
    private static int nextId =0;

    public Player(String name, int balance, GUI_Car car) {
        this.name = name;
        this.balance = balance;
        this.car = car;
        this.id = nextId++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public GUI_Car getCar() {
        return car;
    }

    public void setCar(GUI_Car car) {
        this.car = car;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
