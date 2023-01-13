package entities.dicecup;

import java.util.Arrays;

public abstract class DiceCup {
    protected Die[] dice;

    public abstract void roll();

    public abstract int[] getDiceValues();

    public boolean equals() {
        if (dice.length < 2) {
            return false;
        }
        return Arrays.stream(dice).allMatch(die -> die.equals(dice[0]));
    }

    public abstract int getSum();
}
