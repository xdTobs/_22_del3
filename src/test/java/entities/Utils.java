package entities;

import entities.dicecup.RandomDiceCup;

public class Utils {

    public record Roll(int die1, int die2) {
    }

    public static RandomDiceCup decidableDieCup(Roll ... rolls) {
        return null;
    }

}
