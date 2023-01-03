package Enities.ChanceCards;

import Enities.*;

/**
 * Chance card that inpacts the current players balance, such as "The bank pays you 2$"
 */
public class BalanceChanceCard extends ChanceCard {
    final private int amount;


    public BalanceChanceCard(int amount) {
        this.amount = amount;
        this.desc = "test";
    }

    @Override
    public void executeCardAction(GameBoard gameBoard) {
        gameBoard.getCurrentPlayer().addBalance(amount);

    }

    @Override
    public void executeCardAction(ChanceAction chanceAction) {

    }


}
