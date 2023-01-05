package Enities.DiceCup;


import java.util.Arrays;

public class DiceCup {

    final private Die[] diceArray;

    // int dice defined in Main as the total number of dice in a cup
    public DiceCup() {
        this(new DieSixSided[]{new DieSixSided(6), new DieSixSided(6)});
    }

    public DiceCup(Die[] dice) {
        diceArray = new Die[dice.length];
        for (int i = 0; i < diceArray.length; i++) {
            diceArray[i] = dice[i];
        }
    }

    // Returns the total sum of all dice
    public int getSum() {
        int sum = 0;
        for (Die d : diceArray) {
            sum += d.getFaceValue();
        }
        return sum;
    }

    public boolean diceAreEqual() {
        if (diceArray.length < 2) {
            throw new IllegalArgumentException("One dice can't be equal to anything.");
        }
        Die firstDie = diceArray[0];
        return Arrays.stream(diceArray).allMatch(die -> firstDie.equals(die));
    }

    public int[] getDiceValues() {
        int[] result = new int[diceArray.length];
        for (int i = 0; i < diceArray.length; i++) {
            result[i] = diceArray[i].getFaceValue();
        }
        return result;
    }

    // Rolls all dice
    public void roll() {
        for (Die d : diceArray) {
            d.roll();
        }
    }


}
