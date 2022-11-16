package ChanceCards;

import Enities.Player;
import Language.LanguageHandler;
import gui_fields.GUI_Field;

public class GetOutOfJailChance extends Chance {


    public GetOutOfJailChance() {
        this.desc = LanguageHandler.onGetOutOfJail();
    }

    @Override
    public void executeCardAction(Player p) {
        p.setGetOutOfJailCards(p.getGetOutOfJailCards()+1);
    }
}
