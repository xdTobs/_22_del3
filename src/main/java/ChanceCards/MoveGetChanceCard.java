package ChanceCards;

import View.GUI_Controller;
import Enities.Fields.Street;
import Language.LanguageHandler;
import Enities.*;

public class MoveGetChanceCard extends ChanceCard {
    Street street;
    int pos;
    GameHandler gameHandler;

    GUI_Controller gui_controller;

    public MoveGetChanceCard(int pos, GameHandler gameHandler) {
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
