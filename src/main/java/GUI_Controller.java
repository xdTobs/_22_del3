import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_main.GUI;

import java.util.SplittableRandom;

public class GUI_Controller {
    GUI gui;
Player[] players;
    GUI_Player[] GUI_Players;
    GUI_Field[] fields;

    public GUI_Controller(GUI gui, Player[] players, GUI_Field[] fields){
        this.gui = gui;
        this.fields = fields;
        this.players = players;
        GUI_Players = new GUI_Player[players.length];
        for (int i = 0; i < players.length; i++) {
            GUI_Players[i] = new GUI_Player(players[i].getName(),players[i].getBalance(),players[i].getCar());
        }

    }

    public void addPlayersToGUI(){
        for (GUI_Player player :
                GUI_Players) {
            gui.addPlayer(player);
    }
    }
    public void movePlayer(int pos, int playerId) {

        GUI_Players[playerId].getCar().setPosition(fields[pos]);

    }

    public void updatePlayer(int id){
    Player p =players[id];
    GUI_Player gp = GUI_Players[id];
    gp.setBalance(p.getBalance());
    }

    public void displayText(String text){
        gui.showMessage(text);
    }




}
