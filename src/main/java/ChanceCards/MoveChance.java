package ChanceCards;

import Controllers.GUI_Controller;
import Enities.Player;
import Language.LanguageHandler;
import gui_fields.GUI_Field;

public class MoveChance extends Chance {
    GUI_Field field;
    int pos;

    public MoveChance(int pos) {
//        this.gui_controller = gui_controller;
        this.desc = LanguageHandler.moveTo() + " " + field.getTitle();
        this.pos = pos;
    }

    public MoveChance(GUI_Controller gui_controller, int id) {
        this.gui_controller = gui_controller;
        this.field = gui_controller.getFields()[id];
        this.desc = LanguageHandler.moveTo() + " " + field.getTitle();
        this.pos = id;
    }

    @Override
    public void pullCard(Player p) {
        p.setPos(pos);
        p.getCar().setPosition(field);
    }
}
