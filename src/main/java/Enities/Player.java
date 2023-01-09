package Enities;


public class Player {
    private int balance;
    private int position;
    private boolean jailed;
    private int getOutOfJailCards = 0;
    private int jailedCounter;

    private int extraTurnCounter;
    private String name;


    public Player(String name) {
        this(name, 30000);
    }


    public Player(String name, int balance) {
        this.balance = balance;
        this.position = 0;
        this.jailed = false;
        this.name = name;
        this.jailedCounter = 0;

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

    public void setPosition(int position) {
        this.position = position;
        assert position >= 0 && position <= 39;
    }

    public int getGetOutOfJailCards() {
        return getOutOfJailCards;
    }

    public void addGetOutOfJailCard() {
        setGetOutOfJailCards(getGetOutOfJailCards() + 1);
    }

    public void removeGetOutOfJailCard() {
        setGetOutOfJailCards(getGetOutOfJailCards() - 1);
    }

    public void setGetOutOfJailCards(int getOutOfJailCards) {
        this.getOutOfJailCards = getOutOfJailCards;
    }

    public void addToJailedCounter() {
        jailedCounter++;
    }

    public int getJailedCounter() {
        return jailedCounter;
    }

    public void setJailedCounter(int jailedCounter) {
        this.jailedCounter = jailedCounter;
    }

    public String getName() {
        return name;
    }


    public int getHouses() {
        return 0;
        //TODO implement

    }

    public int getHotels() {
        return 0;
    }
        //TODO implement


    // TODO implement, used in tax field.
    public double totalValue() {
        return 2.0;
    }

    public boolean isBankrupt() {
        return this.getBalance() < 0;
    }
}
