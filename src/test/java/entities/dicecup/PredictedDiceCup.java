package entities.dicecup;

import entities.Utils;

public class PredictedDiceCup extends DiceCup {
    Utils.Roll[] rolls;
    int curRoll;

    public PredictedDiceCup(Utils.Roll... rolls){
        this.rolls = rolls;
        curRoll=0;
    }
    public void roll(){
        if (curRoll==rolls.length){
            throw new RuntimeException("Not enough rolls predetermined");
        }
    dice = new TestDie[]{new TestDie(rolls[curRoll].die1()),new TestDie(rolls[curRoll].die2())};
    curRoll++;

    }

    @Override
    public int[] getDiceValues() {
        return new int[]{dice[0].faceValue,dice[1].faceValue};
    }

    @Override
    public boolean diceAreEqual() {
        return dice[0].faceValue == dice[1].faceValue;
    }

    @Override
    public int getSum() {
        int sum = 0;
        for(Die die : dice){
          sum+=die.faceValue;
        }
        return sum;
    }
    public boolean allRollsUsed(){
        return curRoll==rolls.length;
    }

}
