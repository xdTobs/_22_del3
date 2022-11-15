package Controllers;

import Enities.Player;
import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_main.GUI;

public class GUI_Controller {
    GUI gui;
    GUI_Player[] GUI_Players;
    GUI_Field[] fields;

    public GUI_Controller(GUI_Player[] players, GUI_Field[] fields) {
        this.fields = fields;
        this.gui = new GUI(this.fields);
        this.GUI_Players = players;
        addPlayersToGUI();
    }

    public void addPlayersToGUI() {
        for (GUI_Player player :
                GUI_Players) {
            gui.addPlayer(player);
        }
    }

    public void movePlayer(int pos, Player player) {
        player.getCar().setPosition(fields[pos]);
    }


    public void displayText(String text) {
        gui.showMessage(text);
    }

    public GUI getGui() {
        return gui;
    }

    public GUI_Field[] getFields() {
        return fields;
    }

    public void showMessage(String s) {
        gui.showMessage(s);
    }

    public void setDie(int diceSum) {
        gui.setDie(diceSum);
    }
}

