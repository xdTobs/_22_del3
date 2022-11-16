package ChanceCards;

import Controllers.GUI_Controller;
import Controllers.GameHandler;
import Language.LanguageHandler;
import gui_fields.GUI_Street;
import Enities.*;

public class MoveGetChance extends Chance {
    GUI_Street street;
    int pos;
    GameHandler gameHandler;

    GUI_Controller gui_controller;

    public MoveGetChance(GUI_Controller gui_controller, int pos, GameHandler gameHandler) {
        this.gui_controller = gui_controller;
        this.gameHandler = gameHandler;
        this.pos = pos;
        this.desc = LanguageHandler.moveTo() + " " + LanguageHandler.getFieldName(pos);
    }

    @Override
    public void pullCard(Player p) {
        p.setPosition(pos);
        p.getCar().setPosition(street);
        if (street.getOwnerName().equals("Bank")) {
            p.setBalance(p.getBalance() + Integer.parseInt(street.getRent()));
            this.gameHandler.gameBoard.onStreet(street, p, gui_controller, gameHandler.players);
        } else {
            gameHandler.gameBoard.onStreet(street, p, gui_controller, gameHandler.players);
        }
    }
}
