package View;

import Enities.DiceCup;
import Enities.Fields.*;
import Enities.Player;
import gui_fields.*;
import gui_main.GUI;

public class GUI_View {
    private GUI gui;
    private GUI_Player[] guiPlayers;
    private GUI_Field[] guiFields = new GUI_Field[24];

    public GUI_View(Player[] players, Field[] fields) {
        super();

        guiPlayers = new GUI_Player[players.length];
        for (int i = 0; i < players.length; i++) {
            guiPlayers[i] = new GUI_Player(players[i].getName(), players[i].getBalance());
        }

        for (int i = 0; i < fields.length; i++) {
            if (fields[i] instanceof Start) {
                guiFields[i] = new GUI_Start();
            }
            if (fields[i] instanceof Street) {
                guiFields[i] = new GUI_Street();
            }
            if (fields[i] instanceof Chance) {
                guiFields[i] = new GUI_Chance();
            }
            if (fields[i] instanceof Jail) {
                guiFields[i] = new GUI_Jail();
            }
            if (fields[i] instanceof Parking) {
                guiFields[i] = new GUI_Refuge();
            }
            if (fields[i] instanceof GoToJail) {
                guiFields[i] = new GUI_Jail();
            }

        }
        this.gui = new GUI(guiFields);
    }

    public void updateDie(DiceCup diceCup) {
        gui.setDie(diceCup.getSum());
    }

    public void updatePlayerBalances(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            GUI_Player guiPlayer = guiPlayers[i];
            guiPlayer.setBalance(players[i].getBalance());
        }
    }

    public void updatePlayerLocations(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            GUI_Player guiPlayer = guiPlayers[i];
            guiPlayer.getCar().setPosition(guiFields[players[i].getPosition()]);
        }
    }

    public void showMessage(String string) {
        gui.showMessage(string);
    }

    public void update(DiceCup diceCup, Player[] players, Field[] fields) {
        updatePlayerLocations(players);
        updatePlayerBalances(players);
        updateDie(diceCup);
    }
}
