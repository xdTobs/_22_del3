package ChanceCards;

import Controllers.GUI_Controller;
import Controllers.GameHandler;
import Language.LanguageHandler;
import gui_fields.GUI_Street;
import Enities.*;

public class MoveGetChance extends Chance {
    GUI_Street street;
    GameHandler game;
    int id;

    public MoveGetChance(GUI_Controller gui_controller, int id, GameHandler game) {
        this.gui_controller = gui_controller;
        this.street = (GUI_Street) gui_controller.getFields()[id];
        this.desc = LanguageHandler.moveTo() + " " + street.getTitle();
        this.game = game;
        this.id = id;
    }

    @Override
    public void pullCard(Player p) {
        p.setPos(id);
        p.getCar().setPosition(street);
        if (street.getOwnerName().equals("Bank")) {
            p.setBalance(p.getBalance() + Integer.parseInt(street.getRent()), gui_controller);
            game.onStreet(street, p);
        } else game.onStreet(street, p);


    }
}
