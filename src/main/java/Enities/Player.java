package Enities;

import gui_fields.GUI_Car;
import gui_fields.GUI_Player;

public class Player {
    private int balance;
    private GUI_Player guiPlayer;
    private int pos;
    private boolean jailed;


    public Player(String name, int balance, GUI_Car guiCar) {
        this.balance = balance;
        this.guiPlayer = new GUI_Player(name, balance, guiCar);
        this.pos = 0;
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

    public GUI_Car getCar() {
        return guiPlayer.getCar();
    }

    public int getPos() {
        return pos;
    }

    public void setPosition(int pos) {
        this.pos = pos;
    }

    public GUI_Player getGuiPlayer() {
        return guiPlayer;
    }
}
