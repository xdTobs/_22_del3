package Enities.ChanceCards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;


class DeckTest {
    Deck deck;

    @BeforeEach
    void setUp() {
        List<ChanceCard> cards = new ArrayList<ChanceCard>();
        cards.add(new MoveSpacesChanceCard(3, "You moved three space."));
        this.deck = new Deck(cards);
    }
    @Test
    void deckShouldContainCards() {
        assertTrue(this.deck.getSize() > 0);
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
