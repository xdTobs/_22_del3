package ChanceCards;

import Enities.Player;
import Controllers.*;

public abstract class Chance {

    protected String desc;
    protected GUI_Controller gui_controller;
    protected Language l = new Language();
    public void pullCard(Player p){};


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}


