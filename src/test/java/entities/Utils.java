package entities;

import controller.UserIO;
import entities.chancecards.Deck;
import entities.chancecards.MoveToBreweryChanceCard;
import entities.dicecup.RandomDiceCup;
import entities.fields.*;

public class Utils {

    public record Roll(int die1, int die2) {
    }

    public static RandomDiceCup decidableDieCup(Roll ... rolls) {
        return null;
    }

}
