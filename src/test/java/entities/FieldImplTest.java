package entities;

import controller.*;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

class FieldImplTest {


    private GameBoard gameBoard;
    private GameController gameController;

    @BeforeEach
    void setUp() {
        //Number of fields
        Field[] fields = new Field[6];
        //Type of fields
        fields[0] = new Start("Start");
        fields[1] = new Parking("Parking");
        fields[2] = new Street("Test Street1", 4000, 1000, new int[]{50, 100, 200, 300, 400, 500});
        fields[3] = new GoToJail("Test go to jail");
        fields[4] = new Street("Test Street2", 3000, 1000, new int[]{50, 100, 200, 300, 400, 500});
        fields[5] = new Jail("Jail");
        fields[2].setPair(new FieldPair(Color.orange, new int[]{2, 4}));
        fields[4].setPair(new FieldPair(Color.orange, new int[]{2, 4}));
        BasicUserIO basicUserIO = new BasicUserIO() {
            // We say yes to streets and no to everything else.
            // This means we always say no to buying new house.
            @Override
            public int promptChoice(Message message, Message... choices) {
                if (message.getType() == Message.Type.WANT_TO_BUY_FIELD) {
                    return 0;
                } else {
                    return 1;
                }
            }

            @Override
            public int promptRange(Message message, int min, int max) {
                return 2;
            }

            @Override
            public String promptString(Message message) {
                return "";
            }

            @Override
            public void showMessage(Message message) {
            }
        };
        TestUserIO testUserIO = TestUserIO.debugSetup(basicUserIO);

        //Test dice, that moves you one step.
        RandomDiceCup randomDiceCup = Utils.predeterminedDiceCup(new Utils.Roll(1, 1));
        Deck deck = Deck.setup();
        //Making the gameboard
        Player[] players = PlayerTest.getTwoDebugPlayers(30000);
        gameBoard = new GameBoard(randomDiceCup, fields, deck, testUserIO, players);

        gameController = new

                GameController(new TestView(), testUserIO, gameBoard);
    }

    @Test
    void buyHouseProcess() {
        RandomDiceCup dc = new RandomDiceCup(new TestDie[]{new TestDie(1), new TestDie(2)});
        Field[] fields = GameBoard.getDefaultFields();
        BasicUserIO basicUserIO = new BasicUserIO() {
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
        UserIO testUserIO = new UserIO(basicUserIO);
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

    // TODO
    // Tobias vill du kigge på dette og se så jeg har gjort det rigtigt?
    @Test
    @DisplayName("If player lands on a street and it is owned  by a different player he should pay rent.")
    void payRentToOwner() {
        gameBoard.setRandomDiceCup(new PredeterminedDiceCup(new Utils.Roll(0, 2), new Utils.Roll(0, 2)));
        gameController.playTurn();
        gameBoard.nextPlayer();
        gameController.playTurn();
        Player[] players = gameBoard.getPlayers();
        Player player2 = players[1];
        /*
        Name, Price, HousePrice, {no house, 1 house, 2 houses, 3 houses, 4 houses, hotel}
        new Street("Test Street1", 4000, 1000, new int[]{50, 100, 200, 300, 400, 500});

        Player 1 owns the street, so player 2 should pay rent to player 1.
        Player 2 lands on "Test Street1". He pays 50 in rent.
        Player 2 money should be 30000 - 50 = 29950
         */
        assertEquals(30000 - 50, player2.getBalance());
    }

    @Test
    @DisplayName("If a player buys two streets in a pair that costs 4000 and 3000 he should lose 7000 dkk")
    void buyTwoStreetsTest() {
        gameBoard.setRandomDiceCup(new PredeterminedDiceCup(new Utils.Roll(0, 2), new Utils.Roll(0, 2)));
        gameController.playTurn();
        gameController.playTurn();
        Player player1 = gameBoard.getCurrentPlayer();
        /*
        Name, Price, HousePrice, {no house, 1 house, 2 houses, 3 houses, 4 houses, hotel}
        new Street("Test Street1", 4000, 1000, new int[]{50, 100, 200, 300, 400, 500});

        Player 1 owns the street, so player 2 should pay rent to player 1.
        Player 2 lands on "Test Street1". He pays 50 in rent.
        Player 2 money should be 30000 - 50 = 29950
         */
        assertEquals(30000 - 4000 - 3000, player1.getBalance());
    }

    @Test
    @DisplayName("If a player owns all pairs in a street pair the rent should be doubled, if there are no houses built.")
    void payDoubleRentToOwner() {
        /*
        Name, Price, HousePrice, {no house, 1 house, 2 houses, 3 houses, 4 houses, hotel}
        new Street("Test Street1", 4000, 1000, new int[]{50, 100, 200, 300, 400, 500});
        Player 1 owns the street, so player 2 should pay rent to player 1.
         */
        gameBoard.setRandomDiceCup(new PredeterminedDiceCup(new Utils.Roll(0, 2), new Utils.Roll(0, 2), new Utils.Roll(0, 2)));
        // Player 1 lands on street1.
        gameController.playTurn();
        // Player 1 lands on street2.
        gameController.playTurn();
        gameBoard.nextPlayer();
        // Player 2 lands on "Test Street1". He pays 50 * 2 in rent.
        // Player 2 money should be 30000 - 50 * 2 = 29900
        gameController.playTurn();
        Player[] players = gameBoard.getPlayers();
        Player player2 = players[1];
        assertEquals(30000 - 50 * 2, player2.getBalance());
    }


    @Test
    @DisplayName("If player lands on a street and buys it, pay for it")
    void buyStreetTestOwnershipTest() {
        gameBoard.setRandomDiceCup(new PredeterminedDiceCup(new Utils.Roll(0, 2)));
        gameController.playTurn();
        Player player1 = gameBoard.getCurrentPlayer();
        assertEquals(30000 - 4000, player1.getBalance());
    }

    @Test
    @DisplayName("If player lands on a street and buys it, he should own the street.")
    void buyStreetPaymentTest() {
        gameBoard.setRandomDiceCup(new PredeterminedDiceCup(new Utils.Roll(0, 2)));
        gameController.playTurn();
        Player player1 = gameBoard.getCurrentPlayer();
        Street street = (Street) gameBoard.getFields()[2];
        assertEquals(player1, street.getOwner());
    }

    @Test
    @DisplayName("If a player is in Jail he should not be able to roll recieve rent.")
    void noRentWhenInJailTest() {
        gameBoard.setRandomDiceCup(new PredeterminedDiceCup(new Utils.Roll(0, 2), new Utils.Roll(0, 1), new Utils.Roll(0, 2)));
        gameController.playTurn();
        gameController.playTurn();
        gameBoard.nextPlayer();
        gameController.playTurn();
        Player player1 = gameBoard.getPlayers()[0];
        Player player2 = gameBoard.getCurrentPlayer();
        assertEquals(player1.getBalance(), 26000);
        assertEquals(player2.getBalance(), 30000);
    }

//    fields[0] = new Start("Start");
//    fields[1] = new Parking("Test jail");
//    fields[2] = new Street("Test Street1", 4000, 1000, new int[]{50, 100, 200, 300, 400, 500});
//    fields[3] = new GoToJail("Test go to jail");
//    fields[4] = new Street("Test Street2", 3000, 1000, new int[]{50, 100, 200, 300, 400, 500});
//    fields[5] = new Jail("Jail");

}