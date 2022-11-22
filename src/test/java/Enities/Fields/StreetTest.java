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
        assertEquals("Bank", street1.getOwnerName());
        assertEquals("Bank", street2.getOwnerName());
    }

    @Test
    void setOwner() {
        street1.setOwnerName("Player 1");
        street2.setOwnerName("Player 2");
        assertEquals("Player 1", street1.getOwnerName());
        assertEquals("Player 2", street2.getOwnerName());
    }

    @Test
    void findPositionOfPairStreet() {
        int position1 = street1.getPositionOfPairStreet();
        assertEquals(5, position1);
        int position2 = street2.getPositionOfPairStreet();
        assertEquals(4, position2);
    }
}