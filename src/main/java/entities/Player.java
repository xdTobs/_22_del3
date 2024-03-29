package entities;

public class Player {
    private final String name;
    private int balance;
    private int position;
    private boolean jailed;
    private int getOutOfJailCards = 0;
    private int jailTimeCounter;
    private int sameDieJailCounter;
    private boolean hasLost;

    public Player(String name) {
        this(name, 30000);
    }


    public Player(String name, int balance) {
        this.balance = balance;
        this.position = 0;
        this.jailed = false;
        this.name = name;
        this.jailTimeCounter = 0;

    }

    public static Player[] setupPlayers(String[] names) {
        Player[] players = new Player[names.length];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(names[i]);
        }
        return players;
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
        if (hasLost) {
            return;
        }
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

    public boolean hasGetOutOfJailCard() {
        return getOutOfJailCards > 0;
    }

    public void setGetOutOfJailCards(int getOutOfJailCards) {
        this.getOutOfJailCards = getOutOfJailCards;
    }

    public void addGetOutOfJailCard() {
        setGetOutOfJailCards(getOutOfJailCards + 1);
    }

    public void decrementGetOutOfJailCards() {
        getOutOfJailCards--;
        if (getOutOfJailCards < 0) {
            throw new RuntimeException("Jail cards can't be negative.");
        }
    }

    public void incJailTimeCounter() {
        jailTimeCounter++;
    }

    public int getJailTimeCounter() {
        return jailTimeCounter;
    }

    public void setJailTimeCounter(int jailTimeCounter) {
        this.jailTimeCounter = jailTimeCounter;
    }

    public void incSameDieJailCounter(){sameDieJailCounter++;}
    public int getSameDieJailCounter(){return sameDieJailCounter;}
    public void setSameDieJailCounter(int sameDieJailCounter){this.sameDieJailCounter=sameDieJailCounter;}

    public String getName() {
        return name;
    }




    public boolean isBankruptThisTurn() {
        return isBankrupt() && !hasLost;
    }

    public boolean isBankrupt() {
        return this.getBalance() < 0 || hasLost;
    }

    public boolean hasNotLost() {
        return !this.hasLost;
    }

    public void setHasLost(boolean hasLost) {
        this.hasLost = hasLost;
    }

}
