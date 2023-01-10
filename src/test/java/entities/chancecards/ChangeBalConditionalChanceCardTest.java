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

class ChangeBalConditionalChanceCardTest {

    @Test
    void Successful_ChangeBalConditionalTest() {
        //Number of fields
        Field[] fields = new Field[2];
        //Type of fields
        fields[0] = FieldTest.getStartFieldDebug();
        fields[1] = new ChanceField("Prøv lykken,2, chance,,,,,,,,");
        //Test dice, that moves you one step.
        DiceCup diceCup = new DiceCup(new TestDie[]{new TestDie(1), new TestDie(0)});
        //TestUserIO for gameboard and gamecontroller
        TestUserIO testUserIO = TestUserIO.debugSetup();
        //Deck with only one type of card
        List<ChanceCard> cards = new ArrayList<>();
        cards.add(new ChangeBalConditionalChanceCard(15000, 1, "This is a ChangeBalConditional Chancecard"));
        Deck deck = new Deck(cards);
        //Making the gameboard
        GameBoard gameBoard = new GameBoard(diceCup, fields, deck, testUserIO, 2);
        //Making the game
        GameController gameController = new GameController(new TestView(), testUserIO, gameBoard);
        gameBoard.getCurrentPlayer().setBalance(0);
        //"Playing" the test
        gameController.playTurn(gameBoard.getCurrentPlayer());
        //Assert statement
        assertEquals(15000, gameBoard.getCurrentPlayer().getBalance());
    }
    @Test
    void Unsuccessful_ChangeBalConditionalTest() {
        //Number of fields
        Field[] fields = new Field[2];
        //Type of fields
        fields[0] = FieldTest.getStartFieldDebug();
        fields[1] = new ChanceField("Prøv lykken,2, chance,,,,,,,,");
        //Test dice, that moves you one step.
        DiceCup diceCup = new DiceCup(new TestDie[]{new TestDie(1), new TestDie(0)});
        //TestUserIO for gameboard and gamecontroller
        TestUserIO testUserIO = TestUserIO.debugSetup();
        //Deck with only one type of card
        List<ChanceCard> cards = new ArrayList<>();
        cards.add(new ChangeBalConditionalChanceCard(15000, 1, "This is a ChangeBalConditional Chancecard"));
        Deck deck = new Deck(cards);
        //Making the gameboard
        GameBoard gameBoard = new GameBoard(diceCup, fields, deck, testUserIO, 2);
        //Making the game
        GameController gameController = new GameController(new TestView(), testUserIO, gameBoard);
        gameBoard.getCurrentPlayer().setBalance(1);
        //"Playing" the test
        gameController.playTurn(gameBoard.getCurrentPlayer());
        //Assert statement
        assertEquals(1, gameBoard.getCurrentPlayer().getBalance());
    }
}