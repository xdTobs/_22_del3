package Enities.DiceCup;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestDieTest {

    @Test
    @DisplayName("Two dice with same value should be equal. This should also be true if they are sibling classes.")
    void equals() {
        Die d0 = new TestDie(6);
        Die d1 = new TestDie(6);
        Die d2 = new TestDie(3);
        assertEquals(d0, d1);
        assertNotEquals(d0, d2);
    }

    @Test
    void roll() {
        Die testDie = new TestDie(6);
        testDie.roll();
        int faceValue = testDie.getFaceValue();
        assertEquals(6, faceValue);
    }
}