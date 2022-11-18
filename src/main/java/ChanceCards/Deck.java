package ChanceCards;

import gui_fields.GUI_Street;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Deck of chance cards
 */
public class Deck {
    // Let's do 4 cards for now. All movechance cards.
    ChanceCard[] cards = new ChanceCard[4];

    public Deck() {
        this.cards = initCards();
    }
    public ChanceCard[] initCards(){
        ArrayList<ChanceCard> cards = new ArrayList<>();
        cards.add(new PickStreetChanceCard(new int[]{1,2,4,5}));
        return cards.toArray(new ChanceCard[0]);
    }

    public ChanceCard pullCard() {
        return cards[(int) (Math.random() * cards.length)];
    }
    public void removeCard(ChanceCard c){
        List<ChanceCard> temp = new ArrayList<>(Arrays.asList(cards));
        temp.remove(c);
        if (temp.size()==0)  cards = initCards();
        else cards = temp.toArray(new ChanceCard[0]);
    }
}



