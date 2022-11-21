package Enities.Fields;

import ChanceCards.ChanceCard;
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
    public void executeFieldAction(GameBoard gameBoard) {
        ChanceCard chanceCard = gameBoard.getLatestChanceCard();
        chanceCard.executeCardAction(gameBoard);

    }

}
