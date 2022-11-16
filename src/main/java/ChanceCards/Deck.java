package ChanceCards;

import Controllers.GUI_Controller;

import java.util.ArrayList;

public class Deck {
    Chance[] cards;

    public Deck() {
        ArrayList<Chance> cards = new ArrayList<>();
        cards.add(new MoveChance(new int[]{1, 2}));
        this.cards = cards.toArray(new Chance[0]);
    }

    public Chance pullCard() {
        return cards[(int) (Math.random() * cards.length)];
    }
}
