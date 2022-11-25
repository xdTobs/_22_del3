package Enities.ChanceCards;

import Enities.GameBoard;

public abstract class ChanceCard {

    protected String desc;

    public abstract void executeCardAction(GameBoard gameBoard);

}


