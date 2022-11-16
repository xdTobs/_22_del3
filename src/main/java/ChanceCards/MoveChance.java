package ChanceCards;

import Controllers.GUI_Controller;
import Enities.Player;
import Language.LanguageHandler;
import gui_fields.GUI_Field;

// MoveChance card allows you to move to a field, or choose between several different fields.
// TODO first implement move to one field.
public class MoveChance extends Chance {
    GUI_Field field;
    int[] position = new int[2];

    public MoveChance(int[] position) {
//        this.gui_controller = gui_controller;
        this.desc = LanguageHandler.moveTo() + " " + LanguageHandler.getFieldName(position[0]);
        this.position = position;
    }

    @Override
    public void executeCardAction(Player p) {
        p.setPosition(position[0]);
        // TODO Update GUI. Solution could be to give ChanceCard to GUI_Controller from GameHandler.
    }
}
