package entities.chancecards;

import controller.GameController;
import controller.TestUserIO;
import entities.GameBoard;
import entities.dicecup.DiceCup;
import entities.dicecup.TestDie;
import entities.fields.*;
import org.junit.jupiter.api.Test;
import view.TestView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChangeBalFromPlayersChanceCardTest {

    @Test
    void Postitive_ChangeBalFromPlayerChanceCard() {
        //Number of fields
        Field[] fields = new Field[2];
        //Type of fields
        fields[0] = FieldTest.getStartFieldDebug();
        fields[1] = new ChanceField("Prøv lykken,2, chance,,,,,,,,", 2);
        //Test dice, that moves you one step.
        DiceCup diceCup = new DiceCup(new TestDie[]{new TestDie(1), new TestDie(0)});
        //TestUserIO for gameboard and gamecontroller
        TestUserIO testUserIO = TestUserIO.debugSetup();
        //Deck with only one type of card
        List<ChanceCard> cards = new ArrayList<>();
        cards.add(new ChangeBalFromPlayersChanceCard(-1000, "Change balance from player chance card"));
        Deck deck = new Deck(cards);
        //Making the gameboard
        GameBoard gameBoard = new GameBoard(diceCup, fields, deck, testUserIO, 2);
        //Making the game
        GameController gameController = new GameController(new TestView(), testUserIO, gameBoard);
        //"Playing" the test
        gameController.playTurn(gameBoard.getCurrentPlayer());
        //Assert statement
        assertEquals(29000, gameBoard.getCurrentPlayer().getBalance());
    }

}