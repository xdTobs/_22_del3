package ChanceCards;

import View.*;
import Enities.*;

public class BalanceChanceCard extends ChanceCard {
    private int amount;

    public BalanceChanceCard(String desc, GUI_Controller gui_controller, int amount) {
        this.gui_controller = gui_controller;
        this.amount = amount;
        this.desc = desc;

    }

    @Override
    public void executeCardAction(Player p) {
        p.addBalance(amount);
    }


}
