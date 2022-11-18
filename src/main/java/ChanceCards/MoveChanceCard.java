package ChanceCards;

import Enities.Player;
import Language.LanguageHandler;
import gui_fields.GUI_Field;

// MoveChance card allows you to move to a field, or choose between several different fields.
// TODO first implement move to one field.

/**
 * Chance card to move to a given field.
 */
public class MoveChanceCard extends ChanceCard {
    int position;

    public MoveChanceCard(int position) {
        this.desc = LanguageHandler.moveTo() + " " + LanguageHandler.getFieldName(position);
        this.position = position;
    }

    @Override
    public void executeCardAction(Player[] players, Player p) {

            p.setPosition(position);



        // TODO Update GUI. Solution could be to give ChanceCard to GUI_Controller from GameHandler.
    }
}
