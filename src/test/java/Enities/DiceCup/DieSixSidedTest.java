package Enities.DiceCup;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DieSixSidedTest {

    @Test
    @DisplayName("When rolling 6000 times every roll should appear between 900 and 1100 times.")
    void roll() {
        int[] numberOfRolls = new int[6];
        Die die = new DieSixSided();
        for (int i = 0; i < 6000; i++) {
            die.roll();
            int faceValue = die.getFaceValue();
            numberOfRolls[faceValue - 1]++;
        }
        for (int i = 0; i < 6; i++) {
            assertTrue(numberOfRolls[i] > 900);
            assertTrue(numberOfRolls[i] < 1100);
        }
    }

}