package Enities;

import gui_fields.GUI_Car;
import gui_fields.GUI_Player;

public class Player {
    private int balance;
    private GUI_Player guiPlayer;
    private int position;
    private boolean jailed;
    private int getOutOfJailCards = 0;


    public Player(String name, int balance, GUI_Car guiCar) {
        this.balance = balance;
        this.guiPlayer = new GUI_Player(name, balance, guiCar);
        this.position = 0;
        this.jailed = false;

    }

    public boolean isJailed() {
        return jailed;
    }

    public void setJailed(boolean jailed) {
        this.jailed = jailed;
    }

    public String getName() {
        return guiPlayer.getName();
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
        this.getGuiPlayer().setBalance(balance);
    }

    public void addBalance(int balance) {
        setBalance(this.balance + balance);
    }

    public GUI_Car getCar() {
        return guiPlayer.getCar();
    }

    public int getPosition() {
        return position;
    }

    public void moveForwards(int steps) {
        this.position += steps;
    }

    public GUI_Player getGuiPlayer() {
        return guiPlayer;
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
}
