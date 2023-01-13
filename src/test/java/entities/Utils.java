package entities;

import controller.UserIO;
import entities.chancecards.ChanceCard;
import entities.chancecards.Deck;
import entities.chancecards.MoveToBreweryChanceCard;
import entities.dicecup.DiceCup;
import entities.dicecup.TestDie;
import entities.fields.*;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public record Roll(int die1, int die2) {
    }

    public static DiceCup decidableDieCup(Roll ... rolls) {
        return null;
    }

    public static GameBoard simpleBoard(UserIO userIO) {
        //Number of fields
        Field[] fields = new Field[4];
        //Type of fields
        fields[0] = new Start("Start");
        fields[1] = new ChanceField("Test Chance");
        fields[2] = new Ferry("Test Ferry", 1000, new int[]{1000, 2000, 3000, 4000});
        fields[3] = new Brewery("Test Brewery", 1000, new int[]{1000, 2000, 3000, 4000});

        //Test dice, that moves you one step.
        DiceCup diceCup = decidableDieCup(new Roll(1, 1));
        Deck deck = Deck.of(new MoveToBreweryChanceCard("This is a brewery chance card"));
        //Making the gameboard
        Player[] players = PlayerTest.getTwoDebugPlayers(30000);
        GameBoard gameBoard = new GameBoard(diceCup, fields, deck, userIO, players);


        //Making the game
        return gameBoard;
    }
}
