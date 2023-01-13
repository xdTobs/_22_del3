package entities.chancecards;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


class DeckTest {
    Deck deck;

    @BeforeEach
    void setUp() {
        List<ChanceCard> cards = new ArrayList<ChanceCard>();
        cards.add(new MoveSpacesChanceCard(3, "You moved three space."));
        this.deck = new Deck(cards);
    }

    @Test
    void shouldReturnCorrectCard() {
        assertTrue(deck.pullChanceCard() instanceof ChanceCard);
    }
}
