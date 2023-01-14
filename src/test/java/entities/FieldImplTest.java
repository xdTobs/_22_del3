package entities;

import controller.*;
import controller.TestUserIO;
import entities.chancecards.Deck;
import entities.dicecup.PredeterminedDiceCup;
import entities.dicecup.RandomDiceCup;
import entities.dicecup.TestDie;
import entities.fields.*;
import language.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.TestView;

import java.awt.*;
import java.util.ArrayList;

import static entities.Utils.predeterminedDiceCup;
import static org.junit.jupiter.api.Assertions.*;

class FieldImplTest {


    private GameBoard gameBoard;
    private GameController gameController;

    @BeforeEach
    void setUp() {
        //Number of fields
        Field[] fields = new Field[12];
        //Type of fields
        fields[0] = new Start("Start");
        fields[1] = new Jail("Test jail");
        fields[2] = new Street("Test Street1", 1000, 1000, new int[]{1000, 2000, 3000, 4000});
        fields[3] = new Jail("Test jail");
        fields[4] = new Street("Test Street2", 1000, 1000, new int[]{1000, 2000, 3000, 4000});
        fields[2].setPair(new FieldPair(Color.orange, new int[]{2, 4}));
        fields[4].setPair(new FieldPair(Color.orange, new int[]{2, 4}));
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
    void buyHouseProcess() {
        RandomDiceCup dc = new RandomDiceCup(new TestDie[]{new TestDie(1), new TestDie(2)});
        Field[] fields = GameBoard.getDefaultFields();
        BasicUserIO userIO = new BasicUserIO() {
            @Override
            public int promptChoice(Message message, Message... choices) {
                //override the choice to always want to buy a house on the first avaliable street
                if (message.getType().equals(Message.selectHouse().getType()))
                    return 1;
                return 0;
            }

            @Override
            public int promptRange(Message message, int min, int max) {
                throw new RuntimeException("shouldnt be here in this test");
            }

            @Override
            public String promptString(Message message) {
                return null;
            }

            @Override
            public void showMessage(Message message) {

            }
        };
        UserIO testUserIO = new UserIO(userIO);
        Player[] players = PlayerTest.getTwoDebugPlayers(30000);
        GameBoard localGameBoard = new GameBoard(dc, fields, testUserIO, players);
        View view = new TestView();

        GameController gameController = new GameController(view, testUserIO, localGameBoard);
        gameController.resetPlayerPositions();

        //Hardcode giving the first pair of streets to player 1
        Street street1 = (Street) localGameBoard.getField(1);
        street1.setOwner(localGameBoard.getCurrentPlayer());
        Street street2 = (Street) localGameBoard.getField(3);
        street2.setOwner(localGameBoard.getCurrentPlayer());
        //also add this to the ownership map, since they are not bought through normal process
        localGameBoard.getOwnershipMap().get(localGameBoard.getCurrentPlayer()).add(street1);
        localGameBoard.getOwnershipMap().get(localGameBoard.getCurrentPlayer()).add(street2);
        //play a turn for player 1 and player 2, where you always buy all of the houses you can
        //since player 1 already owns a pair, they buy four houses and a hotel first turn, and player 2 pays rent for all of it
        for (int i = 0; i < 2; i++) {
            gameController.playTurn();
            localGameBoard.nextPlayer();
        }
        //player2 balance should be 24000 since they pay rent with hotel for 6000
        assertEquals(24000, localGameBoard.getPlayers()[1].getBalance());
        //player1 balance should be 26000 since they buy houses for 10000 and recieve 6000 rent
        assertEquals(26000, localGameBoard.getPlayers()[0].getBalance());

    }

    @Test
    @DisplayName("If player lands on go to jail, he should get automatically moved to jail and his status should be set to jailed")
    void gotoJail() {
        Field[] fields = new Field[3];
        fields[0] = new Start("Start, test field");
        fields[1] = new GoToJail("Go to jail test field");
        fields[2] = new Jail("Jail test field");

        RandomDiceCup randomDiceCup = new RandomDiceCup(new TestDie[]{new TestDie(1), new TestDie(0)});
        TestUserIO testUserIO = TestUserIO.debugSetup();
        Deck deck = new Deck(new ArrayList<>());
        Player[] players = PlayerTest.getTwoDebugPlayers(30000);
        GameBoard gameBoard = new GameBoard(randomDiceCup, fields, deck, testUserIO, players);
        GameController gameController = new GameController(new TestView(), testUserIO, gameBoard);

        // Player should go one step, land on go to jail and then get moved to jail.
        gameController.playTurn();


        assertEquals(2, gameBoard.getCurrentPlayer().getPosition());
        assertTrue(gameBoard.getCurrentPlayer().isJailed());
    }

    void streetAction() {
    }

    @Test
    void streetPayRentToOwner() {
        gameBoard.setRandomDiceCup(new PredeterminedDiceCup(new Utils.Roll(0, 2), new Utils.Roll(0, 2)));
        gameController.playTurn();
        gameBoard.nextPlayer();
        gameController.playTurn();
        Player[] players = gameBoard.getPlayers();
        Player player1 = players[0];
        Player player2 = players[1];
        assertEquals(30000 - 1000, player1.getBalance());
        assertEquals(30000 + 1000, player2.getBalance());
    }
}