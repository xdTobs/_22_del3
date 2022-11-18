package View;

import Enities.DiceCup;
import Enities.Fields.*;
import Enities.Player;
import gui_fields.*;
import gui_main.GUI;

import java.awt.*;

public class GUI_View {
    private GUI gui;
    private GUI_Player[] guiPlayers;
    private GUI_Field[] guiFields = new GUI_Field[24];

    public GUI_View(Player[] players, Field[] fields) {
        super();


        for (int i = 0; i < fields.length; i++) {
            if (fields[i] instanceof Start) {
                guiFields[i] = new GUI_Start();
            }
            if (fields[i] instanceof Street street) {
                guiFields[i] = new GUI_Street();
                guiFields[i].setTitle(street.getName());
                guiFields[i].setSubText(street.getRent() + "");
            }
            if (fields[i] instanceof Chance) {
                guiFields[i] = new GUI_Chance();
            }
            if (fields[i] instanceof Jail jail) {
                guiFields[i] = new GUI_Jail();
                guiFields[i].setSubText(jail.getDescription());
            }
            if (fields[i] instanceof Parking parking) {
                guiFields[i] = new GUI_Refuge();
                guiFields[i].setSubText(parking.getDescription());
            }
            if (fields[i] instanceof GoToJail goToJail) {
                guiFields[i] = new GUI_Jail();
                guiFields[i].setSubText(goToJail.getDescription());
            }

        }
        this.gui = new GUI(guiFields);
        addPlayersToGui(players);
    }


    private void addPlayersToGui(Player[] players) {
        // Player colors. Red player 1, blue player 2, green player 3, yellow player 4.
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};

        guiPlayers = new GUI_Player[players.length];

        for (int i = 0; i < players.length; i++) {
            GUI_Car guiCar = new GUI_Car();
            guiCar.setPrimaryColor(colors[i]);
            guiPlayers[i] = new GUI_Player(players[i].getName(), players[i].getBalance(), guiCar);
            gui.addPlayer(guiPlayers[i]);

        }
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

    public GUI getGui() {
        return gui;
    }
}
