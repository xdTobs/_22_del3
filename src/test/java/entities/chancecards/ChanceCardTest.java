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

    @Test
    void chanceCardUpdatesBalance() {
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
