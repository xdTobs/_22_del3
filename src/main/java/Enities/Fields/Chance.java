package Enities.Fields;

import Enities.ChanceCards.ChanceCard;
import Enities.GameBoard;
import Language.LanguageHandler;

public class Chance extends Field {
    public Chance(String line) {
        super(line);
    }


    @Override
    public void executeFieldAction(GameBoard gameBoard) {
        ChanceCard chanceCard = gameBoard.getDeck().getLatestChanceCard();
        gameBoard.getDeck().shuffleCards();
        chanceCard.executeCardAction(gameBoard.getAcc());
    }
}
