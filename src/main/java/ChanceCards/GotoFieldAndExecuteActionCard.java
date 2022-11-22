package ChanceCards;

import Enities.Fields.Field;
import Enities.Fields.Street;
import Language.LanguageHandler;
import Enities.*;

/**
 * Chance card to move to a space.
 * Get the street for free or pay rent when landed on.
 */
public class GotoFieldAndExecuteActionCard extends ChanceCard {
    final private Field field;

    public GotoFieldAndExecuteActionCard(Field field) {
        this.field = field;
        this.desc = LanguageHandler.moveTo() + " " + LanguageHandler.getFieldName(field.getPosition());
    }

    @Override
    public void executeCardAction(GameBoard gameBoard) {
        gameBoard.getCurrentPlayer().setPosition(field.getPosition());
        field.executeFieldAction(gameBoard);
    }
}
