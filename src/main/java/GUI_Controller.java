import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_main.GUI;

public class GUI_Controller {
    GUI gui;
    Player[] players;
    GUI_Player[] GUI_Players;
    GUI_Field[] fields;

    public GUI_Controller(GUI gui, Player[] players, GUI_Field[] fields){
        this.gui = gui;
        this.players = players;
        this.fields = fields;
        GUI_Players = new GUI_Player[players.length];
        for (int i = 0; i < players.length; i++) {
            GUI_Players[i] = new GUI_Player(players[i].getName(),players[i].getBalance(),players[i].getCar());
        }

    }
    private void updatePlayer(Player player){
        int id =player.getId();
        GUI_Player p= GUI_Players[id];
        p.setName(player.getName());
        p.setBalance(player.getBalance());
    }
    private void updatePlayer(int id){
        Player player = players[id];
        GUI_Player p= GUI_Players[id];
        p.setName(player.getName());
        p.setBalance(player.getBalance());
    }




}
