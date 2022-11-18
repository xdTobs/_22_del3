package ChanceCards;

import Enities.Player;
import Language.LanguageHandler;
import gui_fields.GUI_Field;

// MoveChance card allows you to move to a field, or choose between several different fields.
// TODO first implement move to one field.
public class MoveChanceCard extends ChanceCard {
    GUI_Field field;
    int[] position = new int[2];

    public MoveChanceCard(int[] position) {
//        this.gui_controller = gui_controller;
        this.desc = LanguageHandler.moveTo() + " " + LanguageHandler.getFieldName(position[0]);
        this.position = position;
    }

    @Override
    public void executeCardAction(Player[] players, Player p) {
        if (position.length==1){
            p.setPosition(position[0]);

        }else{
            p.setPosition(position[0]);
            //prompt options on gui using GUI.getUserSelection
            //move to selected option
        }

        // TODO Update GUI. Solution could be to give ChanceCard to GUI_Controller from GameHandler.
    }
}
