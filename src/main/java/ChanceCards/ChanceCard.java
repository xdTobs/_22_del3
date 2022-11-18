package ChanceCards;

import Enities.Player;
import View.*;

public abstract class ChanceCard {

    protected String desc;
    protected GUI_Controller gui_controller;

    public abstract void executeCardAction(Player[] players, Player p);

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}


