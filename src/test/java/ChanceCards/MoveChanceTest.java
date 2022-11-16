package ChanceCards;

import Controllers.GUI_Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class MoveChanceTest {

    @BeforeEach
    void setUp() {
        MoveChance card = new MoveChance(new GUI_Controller(null, null), 2);
    }
    @Test
    void executeCardAction() {
    }
}