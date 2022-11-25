package Enities.Fields;

import Enities.ChanceCards.ChanceCard;
import Enities.GameBoard;
import Language.LanguageHandler;

public class ChanceField extends Field {
    final private String name;


    public ChanceField(int position) {
        super(position);
        this.name = LanguageHandler.getFieldName(position);
    }

    @Override
    public String getName() {
        return name;
    }


    @Override
    public void executeFieldAction(GameBoard gameBoard) {
        ChanceCard chanceCard = gameBoard.getDeck().getLatestChanceCard();
        chanceCard.executeCardAction(gameBoard);

    }

}
