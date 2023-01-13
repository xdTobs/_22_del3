package entities.chancecards;

import controller.GameController;
import controller.TestUserIO;
import entities.GameBoard;
import entities.Player;
import entities.PlayerTest;
import entities.dicecup.RandomDiceCup;
import entities.dicecup.TestDie;
import entities.fields.*;
import org.junit.jupiter.api.Test;
import view.TestView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetOutOfJailChanceCardTest {

    @Test
    void Positive_GetOutOfJailChanceCardTest() {
        Field[] fields = new Field[4];
        fields[0] = new Start("Start");
        fields[1] = new ChanceField("Test Chance");
        fields[2] = new GoToJail("Go to jail");
        fields[3] = new Jail("Jail");


        RandomDiceCup randomDiceCup = new RandomDiceCup(new TestDie[]{new TestDie(1), new TestDie(0)});
        TestUserIO testUserIO = TestUserIO.debugSetup();
        List<ChanceCard> cards = new ArrayList<>();
        cards.add(new GetOutOfJailChanceCard("This is a get out of jail chance card."));
        //check hvor mange chancekort spiller har.
        Deck deck = new Deck(cards);
        Player[] players = PlayerTest.getTwoDebugPlayers(30000);
        GameBoard gameBoard = new GameBoard(randomDiceCup, fields, deck, testUserIO, players);
        GameController gameController = new GameController(new TestView(), testUserIO, gameBoard);
        gameController.playTurn();
        gameController.playTurn();
        gameController.playTurn();


        assertEquals(0, gameBoard.getCurrentPlayer().getPosition());
    }
}