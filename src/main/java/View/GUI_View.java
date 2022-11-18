package View;

import Enities.DiceCup;
import Enities.Player;
import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_main.GUI;

public class GUI_View extends View {
    private GUI gui;
    private GUI_Player[] guiPlayers;
    private GUI_Field[] fields;

    public GUI_View(int numberOfPlayers) {
        super();
        this.fields = new GUI_Field[40];
        for (int i = 0; i < 24; i++) {
            fields[i] = new GUI_Field();
        }
        this.gui = new GUI();
    }

    @Override
    public void updateDie(DiceCup diceCup) {
        gui.setDie(diceCup.getSum());
    }

    @Override
    public void updatePlayerBalances(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            GUI_Player guiPlayer = guiPlayers[i];
            guiPlayer.setBalance(players[i].getBalance());
        }
    }

    @Override
    public void updatePlayerLocations(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            GUI_Player guiPlayer = guiPlayers[i];
            guiPlayer.getCar().setPosition(fields[players[i].getPosition()]);
        }


    }

    @Override
    public void showMessage(String string) {
        gui.showMessage(string);
    }
}
