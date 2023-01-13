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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.TestView;

import java.util.List;

import static entities.Utils.decidableDieCup;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ChanceCardTest {
    GameBoard gameBoard;
    GameController gameController;
    @BeforeEach
    void setUp() {

        //Number of fields
        Field[] fields = new Field[9];
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

        gameController.playTurn();
        //Assert statement
        assertEquals(6, gameBoard.getCurrentPlayer().getPosition());
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
