package ChanceCards;

import Enities.GameBoard;
import Language.LanguageHandler;

// MoveChance card allows you to move to a field, or choose between several different fields.
// TODO first implement move to one field.

/**
 * Chance card to move to a given field. Don't pay rent and don't buy.
 */
public class MoveChanceCard extends ChanceCard {
    int position;

    public MoveChanceCard(int position) {
        this.desc = LanguageHandler.moveTo() + " " + LanguageHandler.getFieldName(position);
        this.position = position;
    }

    @Override
    public void executeCardAction(GameBoard gameBoard) {
            gameBoard.getCurrentPlayer().setPosition(position);
    }
}
