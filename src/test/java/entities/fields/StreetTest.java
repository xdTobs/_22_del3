package entities.fields;

import entities.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StreetTest {


    Street street1 = new Street("Test Street 1", 100, 100, new int[]{200, 200, 300, 400, 500, 600});
    Street street2 = new Street("Test Street 2", 100, 100, new int[]{100, 250, 300, 400, 500, 600});

    @Test
    void getName() {
        assertEquals("Test Street 1", street1.getName());
        assertEquals("Test Street 2", street2.getName());
    }

    @Test
    void getRent() {
        assertEquals(200, street1.getRent(0));
        assertEquals(250, street2.getRent(1));
    }

    @Test
    void getOwner() {
        // Bank owns when null.
        assertNull(street1.getOwner());
        assertNull(street2.getOwner());
    }

    @Test
    void setOwner() {
        Player p = new Player("Test Player");
        street1.setOwner(p);
        assertEquals(p, street1.getOwner());
    }

}