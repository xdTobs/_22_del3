package entities.fields;

import controller.*;
import entities.GameBoard;
import entities.Player;
import entities.PlayerTest;
import entities.Utils;
import entities.chancecards.Deck;
import entities.dicecup.PredeterminedDiceCup;
import entities.dicecup.RandomDiceCup;
import entities.dicecup.TestDie;
import language.Message;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.TestView;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GoToJailTest {


    private GameBoard gameBoard;
    private GameController gameController;

    @BeforeEach
    void setUp() {
        //Number of fields
        Field[] fields = new Field[6];
        //Type of fields
        fields[0] = new Start("Start");
        fields[1] = new Street("Test Street1", 4000, 1000, new int[]{50, 100, 200, 300, 400, 500});
        fields[2] = new Ferry("Test Street2", 4000, new int[]{50, 100, 200, 300});
        fields[3] = new Street("Test Street3", 4000, 1000, new int[]{50, 100, 200, 300, 400, 500});
        fields[4] = new GoToJail("Test go to jail");
        fields[5] = new Jail("Test jail");

        TestUserIO testUserIO = TestUserIO.debugSetup();

        // Test dice, that moves you one step.
        RandomDiceCup randomDiceCup = Utils.predeterminedDiceCup();
        Deck deck = Deck.setup();

        // Making the gameboard
        Player[] players = PlayerTest.getTwoDebugPlayers(30000);
        gameBoard = new GameBoard(randomDiceCup, fields, deck, testUserIO, players);
        gameController = new GameController(new TestView(), testUserIO, gameBoard);
    }

    @Test
    @DisplayName("If player lands on go to jail, he should get automatically moved to jail and his status should be set to jailed")
    void gotoJail() {
        gameBoard.setRandomDiceCup(new PredeterminedDiceCup(new Utils.Roll(1, 3)));
        // Player should go one step, land on go to jail and then get moved to jail.
        gameController.playTurn();
        assertEquals(5, gameBoard.getCurrentPlayer().getPosition());
        assertTrue(gameBoard.getCurrentPlayer().isJailed());
    }

    @AfterEach
    void tearDown() {
        PredeterminedDiceCup dc = (PredeterminedDiceCup) gameBoard.getDiceCup();
        assertTrue(dc.allRollsUsed());
    }


}