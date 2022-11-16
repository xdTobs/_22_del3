package ChanceCards;

import Controllers.GUI_Controller;
import Enities.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class MoveChanceTest {

    @BeforeEach
    void setUp() {
        MoveChance card = new MoveChance(new int[]{1, 2});
    }

    @Test
    void executeCardAction() {
        Player player = new Player("Test", 0, null);
    }
}