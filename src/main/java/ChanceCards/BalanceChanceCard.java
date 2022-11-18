package ChanceCards;

import View.*;
import Enities.*;

/**
 * Chance card that inpacts the current players balance, such as "The bank pays you 2$"
 */
public class BalanceChanceCard extends ChanceCard {
    private int amount;


    public BalanceChanceCard(int amount) {
        this.amount = amount;
        this.desc = "test";

    }

    @Override
    public void executeCardAction(Player[] players,Player p) {
        p.addBalance(amount);

    }


}
