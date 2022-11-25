package Enities.ChanceCards;

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

    // Since the field should be given to the player for free we need to give the player money when he lands on it that then gets subtracted in executeFieldAction.
    @Override
    public void executeCardAction(GameBoard gameBoard) {
        gameBoard.getCurrentPlayer().setPosition(field.getPosition());
        if (field instanceof Street street) {
            if (street.getOwner().equals("Bank")) {
                gameBoard.getCurrentPlayer().addBalance(street.getRent());
            }
        }
        field.executeFieldAction(gameBoard);
    }
}
