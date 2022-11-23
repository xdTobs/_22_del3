package ChanceCards;

import Enities.Fields.Field;
import Enities.Fields.Street;
import Enities.GameBoard;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    ArrayList<ChanceCard> cards = new ArrayList<>();

    public Deck(Field[] fields) {
        addPickStreetCards(fields);
        addGotoFieldCards(fields);
        addGetOutOfJailCards();
        shuffleCards();
    }

    private void shuffleCards() {
        Collections.shuffle(cards);
    }

    private void addGetOutOfJailCards() {
        cards.add(new GetOutOfJailChanceCard());
    }

    private void addPickStreetCards(Field[] fields) {
        // This adds a pick street chance card for all the streets just after corners.
        // When I get a card I can pick between going street in position 1 & 2 or 7 & 8, etc, depending on card.
        // This is for simplicity. If someone wants to add the exact cards in the specification, they can do that.
        // TODO: Add the exact cards from the specification.
        for (int i = 0; i < 4; i++) {
            Field s1 = fields[i * 6 + 1];
            Field s2 = fields[i * 6 + 2];
            ChanceCard pickCard = new PickStreetChanceCard(new Field[]{s1, s2});
            cards.add(pickCard);
        }
    }

    private void addGotoFieldCards(Field[] fields) {
        // Add chance card for going to start.
        cards.add(new GotoFieldAndExecuteActionCard(fields[0]));
        // Add chance card for going to skaterpark.
        cards.add(new GotoFieldAndExecuteActionCard(fields[10]));
        // Add chance card for going to strandpromenaden.
        cards.add(new GotoFieldAndExecuteActionCard(fields[23]));
    }

    public void pullCard() {
        ChanceCard card = cards.remove(0);
        cards.add(card);
    }
    public ChanceCard getLatestChanceCard() {
        return cards.get(0);
    }

}



