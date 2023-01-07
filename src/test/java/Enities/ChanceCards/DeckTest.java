package Enities.ChanceCards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class DeckTest {
    Deck deck;
    @BeforeEach
    void setUp() {
        deck = new Deck();
    }

    @Test
    void deckShouldContainCards() {

    assert deck.getSize()>0;
    }

    @Test
    void deckSizeShouldNotChange() {
        int deckSize = deck.getSize();
        deck.pullCard();
        deck.pullCard();
        deck.pullCard();
        deck.pullCard();
        assert deckSize == deck.getSize();
    }

    @Test
    void deckShouldReturnObject() {
        assert (deck.takeChanceCard() != null);
    }

    
}
