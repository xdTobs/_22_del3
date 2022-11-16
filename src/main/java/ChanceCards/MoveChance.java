package ChanceCards;

import Controllers.GUI_Controller;
import Enities.Player;
import Language.LanguageHandler;
import gui_fields.GUI_Field;

// MoveChance card allows you to move to a field, or choose between several different fields.
// TODO first implement move to one field.
public class MoveChance extends Chance {
    GUI_Field field;
    int[] position;

    public MoveChance(GUI_Controller gui_controller, int position) {
//        this.gui_controller = gui_controller;
        this.field = gui_controller.getFields()[position];
        this.desc = LanguageHandler.moveTo() + " " + field.getTitle();
        this.position[0] = position;
    }

    @Override
    public void executeCardAction(Player p) {
        p.setPosition(position[0]);
        p.getCar().setPosition(field);
    }
}
