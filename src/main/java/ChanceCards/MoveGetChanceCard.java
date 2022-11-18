package ChanceCards;

import View.GUI_Controller;
import Enities.Fields.Street;
import Language.LanguageHandler;
import Enities.*;

import java.util.Arrays;

/**
 * Chance card to move to a space and then buy that space if it is free or pay rent if someone owns it
 */
public class MoveGetChanceCard extends ChanceCard {
    Street street;
    int pos;

    GUI_Controller gui_controller;

    public MoveGetChanceCard(int pos) {
        this.pos = pos;
        this.desc = LanguageHandler.moveTo() + " " + LanguageHandler.getFieldName(pos);
    }

    @Override
    public void executeCardAction(Player[] players, Player currentPlayer) {
        currentPlayer.setPosition(pos);
        if (street.getOwner().equals("Bank")) {
            currentPlayer.addBalance(-street.getRent());
            street.setOwner(currentPlayer.getName());
        } else {
//            gameHandler.gameBoard.onStreet(street, currentPlayer, gui_controller, gameHandler.players);
//            street.getOwner().addBalance(street.getRent());
            Player owner = null;
            for (Player p : players) {
                if (p.getName().equals(street.getOwner())) {
                    owner = p;
                }
            }
            owner.addBalance(street.getRent());
            currentPlayer.addBalance(-street.getRent());
        }
    }
}
