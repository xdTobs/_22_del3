package ChanceCards;

import Enities.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class MoveChanceTestCard {

    @BeforeEach
    void setUp() {
        MoveChanceCard card = new MoveChanceCard(new int[]{1, 2});
    }

    @Test
    void executeCardAction() {
        Player player = new Player("Test", 0);
    }
}