package entities.chancecards;

import controller.GameController;
import controller.TestUserIO;
import entities.GameBoard;
import entities.Player;
import entities.PlayerTest;
import entities.Utils;
import entities.dicecup.PredeterminedDiceCup;
import entities.dicecup.RandomDiceCup;
import entities.fields.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.TestView;

import java.awt.*;
import java.util.List;

import static entities.Utils.predeterminedDiceCup;
import static org.junit.jupiter.api.Assertions.*;


public class ChanceCardTest {
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
        fields[10] = new Start("Start");
        fields[11] = new Start("Start");
        fields[1].setPair(new FieldPair(Color.orange, new int[]{1, 3}));
        fields[3].setPair(new FieldPair(Color.orange, new int[]{1, 3}));
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
    @DisplayName("Player should move to Brewery with MoveToBreweryChanceCard.")
    void moveToBreweryPositive() {
        //setup portion
        gameBoard.setRandomDiceCup(new PredeterminedDiceCup(new Utils.Roll(1, 3)));
        Deck deck = new Deck(List.of(new MoveToBreweryChanceCard("test")));
        gameBoard.setDeck(deck);

        gameController.playTurn();
        //Assert statement
        Field currentField = gameBoard.getField(gameBoard.getCurrentPlayer().getPosition());
        assertTrue(currentField instanceof Brewery);
        assertEquals(6, gameBoard.getCurrentPlayer().getPosition());
    }

    @Test
    void moveToFerryPositive() {
        gameBoard.setRandomDiceCup(new PredeterminedDiceCup(new Utils.Roll(1, 3)));
        Deck deck = new Deck(List.of(new MoveToFerryChanceCard("ferry")));
        gameBoard.setDeck(deck);

        gameController.playTurn();
        //Assert statement
        assertEquals(5, gameBoard.getCurrentPlayer().getPosition());
    }

    @Test
    void moveSpacesPositive() {
        gameBoard.setRandomDiceCup(new PredeterminedDiceCup(new Utils.Roll(1, 3)));
        Deck deck = new Deck(List.of(new MoveSpacesChanceCard(4, "moveSpaces")));
        gameBoard.setDeck(deck);

        gameController.playTurn();
        //Assert statement
        assertEquals(9, gameBoard.getCurrentPlayer().getPosition());
    }

    @Test
    void changeBalConditionalPositive() {
        gameBoard.setRandomDiceCup(new PredeterminedDiceCup(new Utils.Roll(1, 3)));
        Deck deck = new Deck(List.of(new ChangeBalConditionalChanceCard(1, 20000, "ChangeBalConditional")));
        gameBoard.setDeck(deck);
        gameBoard.getCurrentPlayer().setBalance(19999);
        gameController.playTurn();
        assertEquals(20000, gameBoard.getCurrentPlayer().getBalance());
        assertEquals(4, gameBoard.getCurrentPlayer().getPosition());
    }

    @Test
    void changeBalConditionalNegative() {
        gameBoard.setRandomDiceCup(new PredeterminedDiceCup(new Utils.Roll(1, 3)));
        Deck deck = new Deck(List.of(new ChangeBalConditionalChanceCard(1, 20000, "ChangeBalConditional")));
        gameBoard.setDeck(deck);
        gameBoard.getCurrentPlayer().setBalance(20000);
        gameController.playTurn();
        assertEquals(20000, gameBoard.getCurrentPlayer().getBalance());
        assertEquals(4, gameBoard.getCurrentPlayer().getPosition());
    }

    @Test
    void changeBalFromPlayers() {
        gameBoard.setRandomDiceCup(new PredeterminedDiceCup(new Utils.Roll(1, 3)));
        Deck deck = new Deck(List.of(new ChangeBalFromPlayersChanceCard(500, "fromPlayers")));
        gameBoard.setDeck(deck);

        gameController.playTurn();
        assertEquals(30500, gameBoard.getCurrentPlayer().getBalance());
        assertEquals(4, gameBoard.getCurrentPlayer().getPosition());
    }


    @Test
    void getOutOfJailCount() {
        gameBoard.setRandomDiceCup(new PredeterminedDiceCup(new Utils.Roll(1, 3)));
        Deck deck = new Deck(List.of(new GetOutOfJailChanceCard("outOfJailChance")));
        gameBoard.setDeck(deck);

        gameController.playTurn();
        assertEquals(30000, gameBoard.getCurrentPlayer().getBalance());
        assertTrue(gameBoard.getCurrentPlayer().hasGetOutOfJailCard());
    }

    @Test
    void getOutOfJailWorks() {
        gameBoard.setRandomDiceCup(new PredeterminedDiceCup(new Utils.Roll(1, 3), new Utils.Roll(1, 2), new Utils.Roll(2, 1)));
        Deck deck = new Deck(List.of(new GetOutOfJailChanceCard("outOfJailChance")));
        gameBoard.setDeck(deck);

        gameController.playTurn();
        gameController.playTurn();
        //gameController.playTurn();

        assertTrue(gameBoard.getCurrentPlayer().isJailed());
        gameController.playTurn();
        assertEquals(30000, gameBoard.getCurrentPlayer().getBalance());
        assertTrue(!gameBoard.getCurrentPlayer().hasGetOutOfJailCard());
        assertFalse(gameBoard.getCurrentPlayer().isJailed());
        assertEquals(11,gameBoard.getCurrentPlayer().getPosition());
    }

    @Test
    void moveToField() {
        gameBoard.setRandomDiceCup(new PredeterminedDiceCup(new Utils.Roll(1, 3)));
        Deck deck = new Deck(List.of(new MoveToFieldChanceCard(1, "moveto1")));
        gameBoard.setDeck(deck);

        gameController.playTurn();
        //30000 + 4000 - 1000
        assertEquals(33500, gameBoard.getCurrentPlayer().getBalance());
        assertEquals(1, gameBoard.getCurrentPlayer().getPosition());
    }

    @Test
    void payPerProperty() {
        gameBoard.setRandomDiceCup(new PredeterminedDiceCup(new Utils.Roll(1, 3)));
        Deck deck = new Deck(List.of(new PayPerPropertyChanceCard(100, 1000, "payper")));
        gameBoard.setDeck(deck);
        Street field1 = (Street) gameBoard.getFields()[1];
        Street field2 = (Street) gameBoard.getFields()[3];
        field1.setOwner(gameBoard.getCurrentPlayer());
        field2.setOwner(gameBoard.getCurrentPlayer());
        gameBoard.getOwnershipMap().get(gameBoard.getCurrentPlayer()).addAll(List.of(field1, field2));
        field1.setHouses(5);
        field2.setHouses(2);

        gameController.playTurn();
        //30000 - 1000 - 200 - 3000 + whatever for sale. for houses after tax
        assertEquals(31800, gameBoard.getCurrentPlayer().getBalance());
        assertEquals(4, gameBoard.getCurrentPlayer().getPosition());

    }
    @Test
    void goToJailTest(){
        gameBoard.setRandomDiceCup(new PredeterminedDiceCup(new Utils.Roll(1, 3)));
        Deck deck = new Deck(List.of(new GoToJailChanceCard("GoToJailChance")));
        gameBoard.setDeck(deck);

        gameController.playTurn();
        assertEquals(8, gameBoard.getCurrentPlayer().getPosition());
        assertTrue(gameBoard.getCurrentPlayer().isJailed());
    }

    @AfterEach
    void tearDown() {
        PredeterminedDiceCup dc = (PredeterminedDiceCup) gameBoard.getDiceCup();
        assertTrue(dc.allRollsUsed());
    }

}
