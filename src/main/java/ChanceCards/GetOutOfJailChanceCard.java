package ChanceCards;

import Enities.Player;
import Language.LanguageHandler;

public class GetOutOfJailChanceCard extends ChanceCard {


    public GetOutOfJailChanceCard() {
        this.desc = LanguageHandler.onGetOutOfJail();
    }

    @Override
    public void executeCardAction(Player[] players, Player p) {
        p.setGetOutOfJailCards(p.getGetOutOfJailCards()+1);
    }
}
