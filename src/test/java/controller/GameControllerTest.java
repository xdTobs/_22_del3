package controller;

import entities.GameBoard;
import entities.Player;
import entities.PlayerTest;
import entities.Utils;
import entities.chancecards.ChangeBalConditionalChanceCard;
import entities.chancecards.Deck;
import entities.dicecup.PredeterminedDiceCup;
import entities.dicecup.RandomDiceCup;
import entities.fields.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.TestView;

import java.awt.*;
import java.util.List;

import static entities.Utils.predeterminedDiceCup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameControllerTest {
    GameBoard gameBoard;
    GameController gameController;

    @BeforeEach
    void setUp() {

        //Number of fields
        Field[] fields = new Field[12];
        //Type of fields
        fields[0] = new Start("Start");
        fields[1] = new Street("Test Street1", 1000, 1000, new int[]{1000, 2000, 3000, 4000});
        fields[2] = new Start("Start");
        fields[3] = new Street("Test Street2", 1000, 1000, new int[]{1000, 2000, 3000, 4000});
        fields[4] = new ChanceField("Test Chance");
        fields[5] = new Ferry("Test Ferry", 1000, new int[]{1000, 2000, 3000, 4000});
        fields[6] = new Brewery("Test Brewery", 1000, new int[]{1000, 2000, 3000, 4000});
        fields[7] = new GoToJail("Test GoToJail");
        fields[8] = new Jail("Test Jail");
        fields[9] = new Start("Start");
        fields[10] = new ChanceField("Test Chance");
        fields[11] = new Start("Start");
        fields[1].setPair(new FieldPair(Color.orange, new int[]{1, 3}));
        fields[3].setPair(new FieldPair(Color.orange, new int[]{1, 3}));
        fields[5].setPair(new FieldPair(Color.orange, new int []{5}));
        TestUserIO testUserIO = TestUserIO.debugSetup();
        //Test dice, that moves you one step.
        RandomDiceCup randomDiceCup = predeterminedDiceCup(new Utils.Roll(1, 1));
        Deck deck = Deck.setup();
        //Making the gameboard
        Player[] players = PlayerTest.getTwoDebugPlayers(30000);
        gameBoard = new GameBoard(randomDiceCup, fields, deck, testUserIO, players);
        gameController = new GameController(new TestView(), testUserIO, gameBoard);
    }

    @Test
    void threeSameDieGoToJailTest() {
        gameBoard.setRandomDiceCup(new PredeterminedDiceCup(new Utils.Roll(1, 1),new Utils.Roll(1, 1),new Utils.Roll(1, 1)));
        Deck deck = new Deck(List.of(new ChangeBalConditionalChanceCard(1, 20000, "ChangeBalConditional")));
        gameBoard.setDeck(deck);

        gameController.playTurn();
        gameController.playTurn();
        gameController.playTurn();

        assertTrue(gameBoard.getCurrentPlayer().isJailed());
        assertEquals(8, gameBoard.getCurrentPlayer().getPosition());


    }

    @AfterEach
    void tearDown() {
        PredeterminedDiceCup dc = (PredeterminedDiceCup) gameBoard.getDiceCup();
        assertTrue(dc.allRollsUsed());
    }
}