package Enities.Fields;

import ChanceCards.BalanceChanceCard;
import ChanceCards.ChanceCard;
import ChanceCards.Deck;
import Enities.GameBoard;
import Language.LanguageHandler;

public class Chance extends Field {
    private String name;
    private String description;


    public Chance(int position) {
        super(position);
        this.name = LanguageHandler.getFieldName(position);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void executeFieldAction(GameBoard gameBoard) {
        Deck deck =gameBoard.getCards();
        ChanceCard chance = deck.pullCard();
        chance.executeCardAction(gameBoard.getPlayers(), gameBoard.getCurrentPlayer());

    }

}
