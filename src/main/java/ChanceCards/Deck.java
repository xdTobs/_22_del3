package ChanceCards;

import java.util.ArrayList;

public class Deck {
    // Lets do 4 cards for now. All movechance cards.
    ChanceCard[] cards = new ChanceCard[4];

    public Deck() {
        ArrayList<ChanceCard> cards = new ArrayList<>();
        cards.add(new MoveChanceCard(new int[]{1, 2}));
        cards.add(new MoveChanceCard(new int[]{3, 4}));
        this.cards = cards.toArray(new ChanceCard[0]);
    }

    public ChanceCard pullCard() {
        return cards[(int) (Math.random() * cards.length)];
    }
}
