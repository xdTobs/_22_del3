package Enities.Fields;

import Enities.ChanceCards.ChanceCard;
import Enities.GameBoard;

public class Chance extends Field {
    public Chance(String line) {
        super(line);
    }


    @Override
    public Field executeFieldAction(GameBoard gameBoard) {
        ChanceCard chanceCard = gameBoard.getDeck().getLatestChanceCard();
        gameBoard.getDeck().shuffleCards();
        chanceCard.executeCardAction(gameBoard.getAcc());
        return null;
    }
}
