package entities.dicecup;


import java.util.Arrays;

public class DiceCup {

    final private Die[] dice;

    // int dice defined in Main as the total number of dice in a cup
    public DiceCup() {
        this(new DieSixSided[]{new DieSixSided(6), new DieSixSided(6)});
    }

    public DiceCup(Die[] dice) {
        this.dice = dice;
    }

    // Returns the total sum of all dice
    public int getSum() {
        int sum = 0;
        for (Die d : dice) {
            sum += d.getFaceValue();
        }
        return sum;
    }

    public boolean diceAreEqual() {
        if (dice.length < 2) {
            return false;
        }
        // equals is implemented in die class.
        return Arrays.stream(dice).allMatch(die -> die.equals(dice[0]));
    }

    public int[] getDiceValues() {
        int[] result = new int[dice.length];
        for (int i = 0; i < dice.length; i++) {
            result[i] = dice[i].getFaceValue();
        }
        return result;
    }

    // Rolls all dice
    public void roll() {
        for (Die d : dice) {
            d.roll();
        }
    }


}
