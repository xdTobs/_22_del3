package Enities;


public class DiceCup {

    final private Die[] diceArray;

    // Returns the total sum of all dice
    public int getSum() {
        int sum = 0;
        for (Die d : diceArray) {
            sum += d.getFaceValue();
        }
        return sum;
    }

    // Rolls all dice
    public void roll() {
        for (Die d : diceArray) {
            d.roll();
        }
    }

    // int dice defined in Main as the total number of dice in a cup
    public DiceCup() {
        diceArray = new Die[1];
        for (int i = 0; i < 1; i++) {
            diceArray[i] = new Die();
        }
    }


    public DiceCup(int max) {
        diceArray = new Die[2];
        for (int i = 0; i < 2; i++) {
            diceArray[i] = new Die(max);
        }
    }
}
