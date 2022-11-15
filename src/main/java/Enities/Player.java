package Enities;

import gui_fields.GUI_Car;
import Controllers.*;
import gui_fields.GUI_Player;

public class Player {
    private String name;
    private int balance;


    private GUI_Player guiPlayer;
    private int id;
    private static int nextId = 0;
    private int pos;
    private boolean jailed;


    public Player(String name, int balance, GUI_Car guiCar) {

        this.name = name;
        this.balance = balance;
        this.guiPlayer = new GUI_Player(name, balance, guiCar);
        this.id = nextId++;
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
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance, GUI_Controller gui_controller) {
        this.balance = balance;
        this.getGuiPlayer().setBalance(balance);
    }

    public GUI_Car getCar() {
        return guiPlayer.getCar();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public GUI_Player getGuiPlayer() {
        return guiPlayer;
    }
}
