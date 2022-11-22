package Enities.Fields;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StreetTest {

    Street street1 = new Street(4);
    Street street2 = new Street(5);

    @Test
    void getName() {
        assertEquals("Slikbutikken", street1.getName());
        assertEquals("Iskiosken", street2.getName());
    }

    @Test
    void executeFieldAction() {
    }

    @Test
    void getRent() {
        assertEquals(1, street1.getRent());
        assertEquals(1, street2.getRent());
    }

    @Test
    void getOwner() {
        assertEquals("Bank", street1.getOwner());
        assertEquals("Bank", street2.getOwner());
    }

    @Test
    void setOwner() {
        street1.setOwner("Player 1");
        street2.setOwner("Player 2");
        assertEquals("Player 1", street1.getOwner());
        assertEquals("Player 2", street2.getOwner());
    }

    @Test
    void findPositionOfPairStreet() {
        int position1 = street1.findPositionOfPairStreet();
        assertEquals(5, position1);
        int position2 = street2.findPositionOfPairStreet();
        assertEquals(4, position2);
    }
}