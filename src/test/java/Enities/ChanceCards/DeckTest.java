package Enities.ChanceCards;

import Enities.GameBoard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    Deck deck = new Deck((new GameBoard()).getFields());

    // This will need to be changed when we add more chance cards. Final goal is 20.
    // TODO: Add 20 chance cards.
    @Test
    void testCorrectCardsAdded() {
        assertEquals(13, deck.getSize());
    }

    @Test
    void pullCard() {
        deck.pullCard();
        ChanceCard card = deck.getLatestChanceCard();
        assertNotNull(card);
    }

    @Test
    void removeCard() {
        deck.pullCard();
        ChanceCard prevCard = deck.getLatestChanceCard();
        ChanceCard prevCard2 = deck.getLatestChanceCard();
        assertEquals(prevCard, prevCard2);
        deck.pullCard();
        ChanceCard currentCard = deck.getLatestChanceCard();
        assertNotEquals(prevCard, currentCard);
    }
}