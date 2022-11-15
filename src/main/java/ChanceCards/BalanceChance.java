package ChanceCards;

import Controllers.*;
import Enities.*;

public class BalanceChance extends Chance {
    private int amount;

    public BalanceChance(String desc, GUI_Controller gui_controller, int amount) {
        this.gui_controller = gui_controller;
        this.amount = amount;
        this.desc = desc;

    }

    @Override
    public void pullCard(Player p) {
        p.setBalance(p.getBalance() + amount, gui_controller);
    }


}
