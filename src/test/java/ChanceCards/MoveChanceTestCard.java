package ChanceCards;

import Enities.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class MoveChanceTestCard {

    @BeforeEach
    void setUp() {
        MoveChanceCard card = new MoveChanceCard(1);
    }

    @Test
    void executeCardAction() {
        Player player = new Player("Test", 0);
    }
}