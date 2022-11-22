package View;

import Enities.DiceCup;
import Enities.Fields.*;
import Enities.Player;
import Language.LanguageHandler;
import gui_fields.*;
import gui_main.GUI;

import java.awt.*;

public class GUI_View {
    final private GUI gui;
    private GUI_Player[] guiPlayers;
    final private GUI_Field[] guiFields = new GUI_Field[24];

    public GUI_View(Field[] fields) {
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
    }


    public void addPlayersToGui(Player[] players) {
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
        updateHouses(fields);
        updatePlayerBalances(players);
        updateDie(diceCup);
    }

    private void updateHouses(Field[] fields) {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] instanceof Street street) {
                if (!street.getOwner().equals("Bank")) {
                    GUI_Street guiStreet = (GUI_Street) guiFields[i];
                    guiStreet.setHouses(1);
                }
            }
        }

    }

    public String promptPlayer(String[] choices, String playerName) {
        String message = playerName + " " + LanguageHandler.chanceCardMsg() + " " + LanguageHandler.onPickFieldChance();
        return this.gui.getUserSelection(message, choices);
    }

    public int promptPlayerCount() {
        return this.gui.getUserInteger(LanguageHandler.playerCountMsg());
    }
}
