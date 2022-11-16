package ChanceCards;

import Controllers.GUI_Controller;
import Enities.Player;
import Language.LanguageHandler;
import gui_fields.GUI_Field;

public class MoveChance extends Chance {
    GUI_Field field;
    int position;

    public MoveChance(GUI_Controller gui_controller, int position) {
//        this.gui_controller = gui_controller;
        this.field = gui_controller.getFields()[position];
        this.desc = LanguageHandler.moveTo() + " " + field.getTitle();
        this.position = position;
    }

    @Override
    public void pullCard(Player p) {
        p.setPosition(position);
        p.getCar().setPosition(field);
    }
}
