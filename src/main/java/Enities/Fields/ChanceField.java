package Enities.Fields;

import Enities.ChanceCards.ChanceCard;
import Enities.GameBoard;
import Language.LanguageHandler;

public class ChanceField extends Field {
    public ChanceField(String s) {
        super(s);
    }

    @Override
    public String getName() {
        return super.getName();
    }


    @Override
    public void executeFieldAction(GameBoard gameBoard) {
        ChanceCard chanceCard = gameBoard.getDeck().getLatestChanceCard();
        chanceCard.executeCardAction(gameBoard.getAcc());

    }

}
