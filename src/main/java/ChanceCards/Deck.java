package ChanceCards;

import Controllers.GUI_Controller;

import java.util.ArrayList;

public class Deck {
    // Lets do 4 cards for now. All movechance cards.
    Chance[] cards = new Chance[4];

    public Deck() {
        ArrayList<Chance> cards = new ArrayList<>();
        cards.add(new MoveChance(new int[]{1, 2}));
        cards.add(new MoveChance(new int[]{3, 4}));
        this.cards = cards.toArray(new Chance[0]);
    }

    public Chance pullCard() {
        return cards[(int) (Math.random() * cards.length)];
    }
}
