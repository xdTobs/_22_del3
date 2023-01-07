package Enities.ChanceCards;

import Enities.ChanceCardImpl;
import Enities.GameBoard;
import View.TestView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChanceCardTest {
    Deck deck;
    ChanceCardImpl acc;
    GameBoard testBoard;

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

        ChanceCard cc = new ChangeBalChanceCard(500, "");
        testBoard.getCurrentPlayer().setBalance(0);
        cc.executeCardAction(acc);
        assert testBoard.getCurrentPlayer().getBalance() == 500;

    }
}
