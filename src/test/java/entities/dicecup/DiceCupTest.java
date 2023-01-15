package entities.dicecup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceCupTest {
    DiceCup diceCupNotEqual;
    DiceCup diceCupEqual;

    @BeforeEach
    void setUp() {
        this.diceCupNotEqual = new RandomDiceCup(new Die[]{new TestDie(1), new TestDie(2)});
        this.diceCupEqual = new RandomDiceCup(new Die[]{new TestDie(2), new TestDie(2)});
    }

    @Test
    void getSum() {
        assertEquals(4, diceCupEqual.getSum());
        assertEquals(3, diceCupNotEqual.getSum());
    }

    @Test
    void equalDiceValue() {
        assertTrue(diceCupEqual.equalDiceValue());
        assertFalse(diceCupNotEqual.equalDiceValue());
        RandomDiceCup diceCup = new RandomDiceCup();
        diceCup.dice[0].faceValue = 2;
        diceCup.dice[0].faceValue = 2;

    }
}