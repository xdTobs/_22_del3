package ChanceCards;

import View.*;
import Enities.*;

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
