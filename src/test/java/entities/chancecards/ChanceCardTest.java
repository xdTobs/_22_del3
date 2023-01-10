package entities.chancecards;

import controller.GameController;
import controller.TestUserIO;
import entities.GameBoard;
import entities.dicecup.DiceCup;
import entities.dicecup.TestDie;
import entities.fields.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import view.TestView;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ChanceCardTest {
    @BeforeEach
    void setUp() {
//        testBoard = new GameBoard();
//        testBoard.createPlayers(4);
//        deck = new Deck();
//        acc = new ChanceCardImpl(testBoard, new TestView());
    }

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


    static Field createFieldFromClass(Class type, int i) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        if (type.equals(Start.class)) {
            return new Start("Prøv lykken," + i + ", chance,,,,,,,,");
        } else if (type.equals(ChanceField.class)) {
            return new ChanceField("Prøv lykken," + i + ", chance,,,,,,,,");
        } else if (type.equals(Brewery.class)) {
            return new Brewery("Coca Cola," + i + ", brewery,3000,0,100,200,,,,");
        } else if (type.equals(Ferry.class)) {
            return new Ferry("Helsingør - Helsingborg," + i + ", ferry,4000,0,500,1000,2000,4000,,");
        } else if (type.equals(Tax.class)) {
            return new Tax("Indkomstskat," + i + ", tax,4000,,,,,,,");
        } else if (type.equals(Jail.class)) {
            return new Jail("I fængsel/På besøg," + i + ", jail,,,,,,,,");
        } else if (type.equals(GoToJail.class)) {
            return new GoToJail("Fængsel," + i + ", goToJail,,,,,,,,");
        } else if (type.equals(Parking.class)) {
            return new Parking("Parkering," + i + ", refugee,,,,,,,,");
        }
        return new Street("Rødovrevej," + i + ", street,1200,1000,50,250,750,2250,4000,6000");
//        type.getDeclaredConstructor().newInstance();
    }


    public static Field[] debugGetFields(Class[] clazzes) {
        Field[] fields = new Field[clazzes.length];
        for (int i = 0; i < clazzes.length; i++) {
            Class clazz = clazzes[i];
            try {
                fields[i] = createFieldFromClass(clazz, i);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return fields;

    }

    GameBoard debugSetup(Class[] clazzes, List<ChanceCard> chanceCards, int diceValue) {
        Field[] fields = debugGetFields(clazzes);
        DiceCup diceCup = new DiceCup(new TestDie[]{new TestDie(diceValue), new TestDie(0)});

        TestUserIO testUserIO = TestUserIO.debugSetup();

        Deck deck = new Deck(chanceCards);
        return new GameBoard(diceCup, fields, deck, testUserIO, 2);
    }


    @Test
    void chanceCardUpdatesBalance() {
        // First two lines are example for Christian.
        Class[] fieldTypes = new Class[]{Start.class, ChanceField.class, Street.class, Tax.class, Jail.class, GoToJail.class, Parking.class, Brewery.class, Ferry.class};
        GameBoard gameBoardTest = debugSetup(fieldTypes, new ArrayList<>(), 2);

        Field[] fields = new Field[2];

        fields[0] = FieldTest.getStartFieldDebug();
        fields[1] = new ChanceField("Prøv lykken,2, chance,,,,,,,,");

        DiceCup diceCup = new DiceCup(new TestDie[]{new TestDie(1), new TestDie(0)});
        TestUserIO testUserIO = TestUserIO.debugSetup();

        List<ChanceCard> cards = new ArrayList<>();
        cards.add(new ChangeBalChanceCard(1000, "Update balance chance card."));
        Deck deck = new Deck(cards);

        GameBoard gameBoard = new GameBoard(diceCup, fields, deck, testUserIO, 2);
        GameController gameController = new GameController(new TestView(), testUserIO, gameBoard);

        // When player is created he has 30 000. Chance card adds 1000.
        gameController.playTurn(gameBoard.getCurrentPlayer());
        assertEquals(31000, gameBoard.getCurrentPlayer().getBalance());

    }
}
