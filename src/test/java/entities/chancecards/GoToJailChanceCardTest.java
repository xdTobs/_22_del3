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

import static org.junit.jupiter.api.Assertions.*;

class GoToJailChanceCardTest {

    @Test
    void Positive_GoToJailChanceCardTest() {
        Field[] fields = new Field[5];

        fields[0] = new Start("Start");
        fields[1] = new ChanceField("Test Chance");
        fields[2] = new Ferry("Test Ferry", 1000, new int[]{1000, 2000, 3000, 4000});
        fields[3] = new Jail("I fængsel/På besøg");
        fields[4] = new Ferry("Test Ferry", 1000, new int[]{1000, 2000, 3000, 4000});

        RandomDiceCup randomDiceCup = new RandomDiceCup(new TestDie[]{new TestDie(1), new TestDie(0)});
        TestUserIO testUserIO = TestUserIO.debugSetup();
        List<ChanceCard> cards = new ArrayList<>();
        cards.add(new GoToJailChanceCard("This is a GoToJail chance card."));
        Deck deck = new Deck(cards);
        Player[] players = PlayerTest.getTwoDebugPlayers(30000);
        GameBoard gameBoard = new GameBoard(randomDiceCup, fields, deck, testUserIO, players);
        GameController gameController = new GameController(new TestView(), testUserIO, gameBoard);
        gameController.playTurn();

        assertEquals(3, gameBoard.getCurrentPlayer().getPosition());
    }
}