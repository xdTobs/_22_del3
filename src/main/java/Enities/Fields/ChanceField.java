package Enities.Fields;

import Enities.ChanceCards.ChanceCard;
import Enities.GameBoard;

public class ChanceField extends Field {
    public ChanceField(String s) {
        super(s);
    }

    @Override
    public String getName() {
        return super.getName();
    }


    @Override
    public Field executeFieldAction(GameBoard gameBoard) {
        ChanceCard chanceCard = gameBoard.getDeck().getLatestChanceCard();
        gameBoard.getDeck().shuffleCards();
        chanceCard.executeCardAction(gameBoard.getAcc());
        return null;
    }

}
