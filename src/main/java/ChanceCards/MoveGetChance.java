package ChanceCards;

import Controllers.GUI_Controller;
import Controllers.GameHandler;
import Language.LanguageHandler;
import gui_fields.GUI_Street;
import Enities.*;

public class MoveGetChance extends Chance {
    GUI_Street street;
    GameHandler game;
    int pos;

    public MoveGetChance(GUI_Controller gui_controller, int pos, GameHandler game) {
        this.gui_controller = gui_controller;
        this.game = game;
        this.pos = pos;
        this.desc = LanguageHandler.moveTo() + " " + LanguageHandler.getFieldName(pos);
    }

    @Override
    public void pullCard(Player p) {
        p.setPos(pos);
        p.getCar().setPosition(street);
        if (street.getOwnerName().equals("Bank")) {
            p.setBalance(p.getBalance() + Integer.parseInt(street.getRent()));
            game.onStreet(street, p);
        } else game.onStreet(street, p);
    }
}
