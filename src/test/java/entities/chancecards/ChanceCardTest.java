package entities.chancecards;

import controller.GameController;
import controller.TestUserIO;
import entities.GameBoard;
import entities.Player;
import entities.PlayerTest;
import entities.Utils;
import entities.dicecup.PredictedDiceCup;
import entities.dicecup.RandomDiceCup;
import entities.fields.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.TestView;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static entities.Utils.decidableDieCup;
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
        fields[1].setPair(new FieldPair(Color.orange,new int[]{1,3}));
        fields[3].setPair(new FieldPair(Color.orange,new int[]{1,3}));
        TestUserIO testUserIO = TestUserIO.debugSetup();
        //Test dice, that moves you one step.
        RandomDiceCup randomDiceCup = decidableDieCup(new Utils.Roll(1, 1));
        Deck deck = Deck.setup();
        //Making the gameboard
        Player[] players = PlayerTest.getTwoDebugPlayers(30000);
        gameBoard = new GameBoard(randomDiceCup, fields, deck, testUserIO, players);
        gameController = new GameController(new TestView(), testUserIO, gameBoard);


    }

    @Test
    void moveToBreweryPositive() {
        //setup portion
        gameBoard.setRandomDiceCup(new PredictedDiceCup(new Utils.Roll(1,3)));
        Deck deck = new Deck(List.of(new MoveToBreweryChanceCard("test")));
        gameBoard.setDeck(deck);

        gameController.playTurn(gameBoard.getCurrentPlayer());
        //Assert statement
        assertEquals(6, gameBoard.getCurrentPlayer().getPosition());
    }

    @Test
    void moveToFerryPositive() {
        gameBoard.setRandomDiceCup(new PredictedDiceCup(new Utils.Roll(1,3)));
        Deck deck = new Deck(List.of(new MoveToFerryChanceCard("ferry")));
        gameBoard.setDeck(deck);

        gameController.playTurn(gameBoard.getCurrentPlayer());
        //Assert statement
        assertEquals(5, gameBoard.getCurrentPlayer().getPosition());
    }

    @Test
    void moveSpacesPositive() {
        gameBoard.setRandomDiceCup(new PredictedDiceCup(new Utils.Roll(1,3)));
        Deck deck = new Deck(List.of(new MoveSpacesChanceCard(3,"moveSpaces")));
        gameBoard.setDeck(deck);

        gameController.playTurn(gameBoard.getCurrentPlayer());
        //Assert statement
        assertEquals(8, gameBoard.getCurrentPlayer().getPosition());
        //you should get extra turn and get put in jail
        assertTrue(gameBoard.getCurrentPlayer().isJailed());
    }

    @Test
    void changeBalConditionalPositive() {
        gameBoard.setRandomDiceCup(new PredictedDiceCup(new Utils.Roll(1,3)));
        Deck deck = new Deck(List.of(new ChangeBalConditionalChanceCard(1,20000,"ChangeBalConditional")));
        gameBoard.setDeck(deck);
        gameBoard.getCurrentPlayer().setBalance(19999);
        gameController.playTurn(gameBoard.getCurrentPlayer());
        assertEquals(20000,gameBoard.getCurrentPlayer().getBalance());
        assertEquals(4,gameBoard.getCurrentPlayer().getPosition());
    }

    @Test
    void changeBalConditionalNegative() {
        gameBoard.setRandomDiceCup(new PredictedDiceCup(new Utils.Roll(1,3)));
        Deck deck = new Deck(List.of(new ChangeBalConditionalChanceCard(1,20000,"ChangeBalConditional")));
        gameBoard.setDeck(deck);
        gameBoard.getCurrentPlayer().setBalance(20000);
        gameController.playTurn(gameBoard.getCurrentPlayer());
        assertEquals(20000,gameBoard.getCurrentPlayer().getBalance());
        assertEquals(4,gameBoard.getCurrentPlayer().getPosition());
    }

    @Test
    void changeBalFromPlayers() {
        gameBoard.setRandomDiceCup(new PredictedDiceCup(new Utils.Roll(1,3)));
        Deck deck = new Deck(List.of(new ChangeBalFromPlayersChanceCard(500,"fromPlayers")));
        gameBoard.setDeck(deck);

        gameController.playTurn(gameBoard.getCurrentPlayer());
        assertEquals(30500,gameBoard.getCurrentPlayer().getBalance());
        assertEquals(4,gameBoard.getCurrentPlayer().getPosition());
    }



    @Test
    void getOutOfJailCount() {
        gameBoard.setRandomDiceCup(new PredictedDiceCup(new Utils.Roll(1,3)));
        Deck deck = new Deck(List.of(new GetOutOfJailChanceCard("outOfJailChance")));
        gameBoard.setDeck(deck);

        gameController.playTurn(gameBoard.getCurrentPlayer());
        assertEquals(30000,gameBoard.getCurrentPlayer().getBalance());
        assertEquals(1,gameBoard.getCurrentPlayer().getGetOutOfJailCards());
    }
    @Test
    void getOutOfJailWorks() {
        gameBoard.setRandomDiceCup(new PredictedDiceCup(new Utils.Roll(1,3),new Utils.Roll(1,2),new Utils.Roll(2,1)));
        Deck deck = new Deck(List.of(new GetOutOfJailChanceCard("outOfJailChance")));
        gameBoard.setDeck(deck);

        gameController.playTurn(gameBoard.getCurrentPlayer());
        gameController.playTurn(gameBoard.getCurrentPlayer());
        assertEquals(30000,gameBoard.getCurrentPlayer().getBalance());
        assertEquals(0,gameBoard.getCurrentPlayer().getGetOutOfJailCards());
        assertFalse(gameBoard.getCurrentPlayer().isJailed());
    }

    @Test
    void moveToField() {
        gameBoard.setRandomDiceCup(new PredictedDiceCup(new Utils.Roll(1,3)));
        Deck deck = new Deck(List.of(new MoveToFieldChanceCard(1,"moveto1")));
        gameBoard.setDeck(deck);

        gameController.playTurn(gameBoard.getCurrentPlayer());
        //30000 + 4000 - 1000
        assertEquals(33000,gameBoard.getCurrentPlayer().getBalance());
        assertEquals(1,gameBoard.getCurrentPlayer().getPosition());
    }

    @Test
    void payPerProperty() {
        gameBoard.setRandomDiceCup(new PredictedDiceCup(new Utils.Roll(1,3)));
        Deck deck = new Deck(List.of(new PayPerPropertyChanceCard(100,1000,"payper")));
        gameBoard.setDeck(deck);
        Street field1 = (Street) gameBoard.getFields()[1];
        Street field2 = (Street) gameBoard.getFields()[3];
        field1.setOwner(gameBoard.getCurrentPlayer());
        field2.setOwner(gameBoard.getCurrentPlayer());
        gameBoard.getOwnershipMap().get(gameBoard.getCurrentPlayer()).addAll(List.of(field1,field2));
        field1.setHouses(5);
        field2.setHouses(2);

        gameController.playTurn(gameBoard.getCurrentPlayer());
        //30000 - 1000 - 200 - 3000 for houses after tax
        assertEquals(25800,gameBoard.getCurrentPlayer().getBalance());
        assertEquals(4,gameBoard.getCurrentPlayer().getPosition());

    }


    @AfterEach
    void tearDown() {
        PredictedDiceCup dc = (PredictedDiceCup) gameBoard.getDiceCup();
        assertTrue(dc.allRollsUsed());

    }
    //    @BeforeEach
//    void setUp() {
//        testBoard = new GameBoard();
//        testBoard.createPlayers(4);
//        deck = new Deck();
//        acc = new ChanceCardImpl(testBoard, new TestView());
//    }

//    @Test
//    void chanceCardNotNull() {
//        boolean noNulls = true;
//        for (ChanceCard cc : deck.getCards()) {
//            if (cc == null) {
//                noNulls = false;
//                break;
//            }
//        }
//        assert noNulls;
//    }

//    @Test
//    void chanceCardHasDesc() {
//        boolean allDesc = true;
//        for (ChanceCard cc : deck.getCards()) {
//            if (cc.desc == null) {
//                allDesc = false;
//                break;
//            }
//        }
//        assert allDesc;
//    }


//    static Field createFieldFromClass(Class type, int i) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
//
//        if (type.equals(Start.class)) {
//            return new Start("Prøv lykken," + i + ", chance,,,,,,,,");
//        } else if (type.equals(ChanceField.class)) {
//            return new ChanceField("Prøv lykken," + i + ", chance,,,,,,,,", 2);
//        } else if (type.equals(Brewery.class)) {
//            return new Brewery("Coca Cola," + i + ", brewery,3000,0,100,200,,,,");
//        } else if (type.equals(Ferry.class)) {
//            return new Ferry("Helsingør - Helsingborg," + i + ", ferry,4000,0,500,1000,2000,4000,,");
//        } else if (type.equals(Tax.class)) {
//            return new Tax("Indkomstskat," + i + ", tax,4000,,,,,,,");
//        } else if (type.equals(Jail.class)) {
//            return new Jail("I fængsel/På besøg," + i + ", jail,,,,,,,,");
//        } else if (type.equals(GoToJail.class)) {
//            return new GoToJail("Fængsel," + i + ", goToJail,,,,,,,,");
//        } else if (type.equals(Parking.class)) {
//            return new Parking("Parkering," + i + ", refugee,,,,,,,,");
//        }
//        return new Street("Rødovrevej," + i + ", street,1200,1000,50,250,750,2250,4000,6000", position);
//        type.getDeclaredConstructor().newInstance();
//    }


//    public static Field[] debugGetFields(Class[] clazzes) {
//        Field[] fields = new Field[clazzes.length];
//        for (int i = 0; i < clazzes.length; i++) {
//            Class clazz = clazzes[i];
//            try {
//                fields[i] = createFieldFromClass(clazz, i);
//            } catch (NoSuchMethodException e) {
//                throw new RuntimeException(e);
//            } catch (InvocationTargetException e) {
//                throw new RuntimeException(e);
//            } catch (InstantiationException e) {
//                throw new RuntimeException(e);
//            } catch (IllegalAccessException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return fields;
//
//    }
//
//    GameBoard debugSetup(Class[] clazzes, List<ChanceCard> chanceCards, int diceValue) {
//        Field[] fields = debugGetFields(clazzes);
//        DiceCup diceCup = new DiceCup(new TestDie[]{new TestDie(diceValue), new TestDie(0)});
//
//        TestUserIO testUserIO = TestUserIO.debugSetup();
//
//        Deck deck = new Deck(chanceCards);
//        return new GameBoard(diceCup, fields, deck, testUserIO, 2);
//    }


   // @Test
    //void chanceCardUpdatesBalance() {
        // First two lines are example for Christian.
//        Class[] fieldTypes = new Class[]{Start.class, ChanceField.class, Street.class, Tax.class, Jail.class, GoToJail.class, Parking.class, Brewery.class, Ferry.class};
//        GameBoard gameBoardTest = debugSetup(fieldTypes, new ArrayList<>(), 2);
//
//        Field[] fields = new Field[2];
//
//        fields[0] = FieldTest.getStartFieldDebug();
//        fields[1] = new ChanceField("Prov lykken", 2);
//
//        DiceCup diceCup = new DiceCup(new TestDie[]{new TestDie(1), new TestDie(0)});
//        TestUserIO testUserIO = TestUserIO.debugSetup();
//
//        List<ChanceCard> cards = new ArrayList<>();
//        cards.add(new ChangeBalChanceCard(1000, "Update balance chance card."));
//        Deck deck = new Deck(cards);
//
//        GameBoard gameBoard = new GameBoard(diceCup, fields, deck, testUserIO, 2);
//        GameController gameController = new GameController(new TestView(), testUserIO, gameBoard);
//
//        // When player is created he has 30 000. Chance card adds 1000.
//        gameController.playTurn(gameBoard.getCurrentPlayer());
//        assertEquals(31000, gameBoard.getCurrentPlayer().getBalance());

   // }
}
