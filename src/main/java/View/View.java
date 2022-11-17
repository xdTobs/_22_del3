package View;

import Enities.DiceCup;
import Enities.Fields.Field;
import Enities.Player;

public abstract class View {
    public void update(DiceCup diceCup, Player[] players, Field[] fields) {
        updatePlayerLocations(players);
        updatePlayerBalances(players);
        updateDie(diceCup);
    }

    public abstract void updateDie(DiceCup diceCup);

    public abstract void updatePlayerBalances(Player[] players);

    public abstract void updatePlayerLocations(Player[] players);

    public abstract void showMessage(String string);
//        GUI gui;
//        GUI_Player[] GUI_Players;
//        GUI_Field[] fields;
//
//        public GUI_Controller(GUI_Player[] players, GUI_Field[] fields) {
//            this.fields = fields;
//            this.gui = new GUI(this.fields);
//            this.GUI_Players = players;
//            addPlayersToGUI();
//        }
//
//        // This method will update gui by setting all fields in gui to the latest values in the other states.
//        // This will be less prone to bugs I hope. Just call this method everytime you update state, instead of setting individual state of parts of the gui.
//        public void setGuiPlayerPosition(GUI_Player guiPlayer, GUI_Field field) {
//            guiPlayer.getCar().setPosition(field);
//        }
//
//        public void addPlayersToGUI() {
//            for (GUI_Player player :
//                    GUI_Players) {
//                gui.addPlayer(player);
//            }
//        }
//
//        public void movePlayer(int pos, Player player) {
//            player.getCar().setPosition(fields[pos]);
//        }
//
//
//        public void displayText(String text) {
//            gui.showMessage(text);
//        }
//
//        public GUI getGui() {
//            return gui;
//        }
//
//        public GUI_Field[] getFields() {
//            return fields;
//        }
//
//        public void showMessage(String s) {
//            gui.showMessage(s);
//        }
//
//        public void setDie(int diceSum) {
//            gui.setDie(diceSum);
//        }
//    }

}
