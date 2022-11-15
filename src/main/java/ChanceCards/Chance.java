package ChanceCards;

import Enities.Player;
import Controllers.*;
import Language.LanguageHandler;

public abstract class Chance {

    protected String desc;
    protected GUI_Controller gui_controller;
    protected LanguageHandler l = LanguageHandler.getInstance();
    public void pullCard(Player p){};


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}


