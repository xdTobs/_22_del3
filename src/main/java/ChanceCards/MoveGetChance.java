package ChanceCards;

import Controllers.GUI_Controller;
import Controllers.GameHandler;
import Enities.Fields.Street;
import Language.LanguageHandler;
import gui_fields.GUI_Street;
import Enities.*;

public class MoveGetChance extends Chance {
    Street street;
    int pos;
    GameHandler gameHandler;

    GUI_Controller gui_controller;

    public MoveGetChance(int pos, GameHandler gameHandler) {
        this.gameHandler = gameHandler;
        this.pos = pos;
        this.desc = LanguageHandler.moveTo() + " " + LanguageHandler.getFieldName(pos);
    }

    @Override
    public void executeCardAction(Player p) {
        p.setPosition(pos);
        p.getCar().setPosition(street.getGuiField());
        if (street.getOwnerName().equals("Bank")) {
            p.addBalance(street.getRent());
            this.gameHandler.gameBoard.onStreet(street, p, gui_controller, gameHandler.players);
        } else {
            gameHandler.gameBoard.onStreet(street, p, gui_controller, gameHandler.players);
        }
    }
}
