package entities.dicecup;


import java.util.Arrays;

public class RandomDiceCup extends DiceCup {

    // int dice defined in Main as the total number of dice in a cup
    public RandomDiceCup() {
        this(new DieSixSided[]{new DieSixSided(6), new DieSixSided(6)});
    }

    public RandomDiceCup(Die[] dice) {
        this.dice = dice;
    }

    // Returns the total sum of all dice
    @Override
    public int getSum() {
        int sum = 0;
        for (Die d : dice) {
            sum += d.getFaceValue();
        }
        return sum;
    }



    @Override
    public int[] getDiceValues() {
        int[] result = new int[dice.length];
        for (int i = 0; i < dice.length; i++) {
            result[i] = dice[i].getFaceValue();
        }
        return result;
    }

    // Rolls all dice
    @Override
    public void roll() {
        for (Die d : dice) {
            d.roll();
        }
    }


}
