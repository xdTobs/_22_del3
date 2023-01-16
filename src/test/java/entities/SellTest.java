package entities;

import controller.BasicUserIO;
import controller.GameController;
import controller.TestUserIO;
import controller.UserIO;
import entities.chancecards.Deck;
import entities.dicecup.PredeterminedDiceCup;
import entities.dicecup.RandomDiceCup;
import entities.fields.*;
import language.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.TestView;

import java.awt.*;
import java.util.List;

import static entities.Utils.predeterminedDiceCup;
import static org.junit.jupiter.api.Assertions.*;

class SellTest {
    GameController gameController;
    GameBoard gameBoard;

    @BeforeEach
    void setUp() {
        //Number of fields
        Field[] fields = new Field[10];
        //Type of fields
        fields[0] = new Start("Start");
        fields[1] = new Street("Test Street1", 1000, 1000, new int[]{1000, 2000, 3000, 4000});
        fields[2] = new Start("Start");
        fields[3] = new Street("Test Street2", 1000, 1000, new int[]{1000, 2000, 3000, 4000});
        fields[4] = new Start("Start");
        fields[5] = new Ferry("Test Ferry", 1000, new int[]{1000, 2000, 3000, 4000});
        fields[6] = new Brewery("Test Brewery", 1000, new int[]{1000, 2000, 3000, 4000});
        fields[7] = new Start("Start");
        fields[8] = new Start("Start");
        fields[9] = new Start("Start");
        fields[1].setPair(new FieldPair(Color.orange, new int[]{1, 3}));
        fields[3].setPair(new FieldPair(Color.orange, new int[]{1, 3}));
        BasicUserIO basicUserIO = new BasicUserIO() {
            // We say yes to streets and no to everything else.
            // This means we always say no to buying new house.
            @Override
            public int promptChoice(Message message, Message... choices) {
                if (message.getType() == Message.Type.PAWN_SELECT_PROPERTY||message.getType() == Message.Type.WANT_TO_SELL||message.getType() == Message.Type.SELL_SELECT_HOUSE) {
                    return 1;
                } else {
                    return 0;
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
        RandomDiceCup randomDiceCup = predeterminedDiceCup(new Utils.Roll(1, 3));
        Deck deck = Deck.setup();
        //Making the gameboard
        Player[] players = PlayerTest.getTwoDebugPlayers(30000);
        gameBoard = new GameBoard(randomDiceCup, fields, deck, testUserIO, players);
        gameController = new GameController(new TestView(), testUserIO, gameBoard);
    }

    @Test
    void sellHouseTest() {
        gameBoard.setRandomDiceCup(new PredeterminedDiceCup(new Utils.Roll(1, 3)));
        Street street1 = (Street) gameBoard.getFields()[1];
        Street street2 = (Street) gameBoard.getFields()[3];
        RentableField ferry = (RentableField) gameBoard.getFields()[5];
        ferry.setOwner(gameBoard.getCurrentPlayer());
        street1.setOwner(gameBoard.getCurrentPlayer());
        street2.setOwner(gameBoard.getCurrentPlayer());
        gameBoard.getOwnershipMap().get(gameBoard.getCurrentPlayer()).addAll(List.of(street1, street2,ferry));
        street1.setHouses(2);
        street2.setHouses(1);

        gameController.playTurn();
        assertEquals(0,street1.getHouses());
        assertEquals(0,street2.getHouses());
        assertTrue(street1.isPawned());
    }



    @Test
    void pawnPropertyTest() {

    }

}