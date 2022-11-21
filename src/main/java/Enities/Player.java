package Enities;


public class Player {
    private int balance;
    private int position;
    private boolean jailed;
    private int getOutOfJailCards = 0;
    private String name;


    public Player(String name, int balance) {
        this.balance = balance;
        this.position = 0;
        this.jailed = false;
        this.name=name;

    }

    public boolean isJailed() {
        return jailed;
    }

    public void setJailed(boolean jailed) {
        this.jailed = jailed;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void addBalance(int balance) {
        setBalance(this.balance + balance);
    }

    public int getPosition() {
        return position;
    }

    public void moveForwards(int steps) {
        this.position += steps;
    }

    public void setPosition(int position) {
        this.position = position;
        assert position >= 0 && position <= 23;
    }

    public int getGetOutOfJailCards() {
        return getOutOfJailCards;
    }

    public void setGetOutOfJailCards(int getOutOfJailCards) {
        this.getOutOfJailCards = getOutOfJailCards;
    }

    public String getName() {
        return name;
    }
}
