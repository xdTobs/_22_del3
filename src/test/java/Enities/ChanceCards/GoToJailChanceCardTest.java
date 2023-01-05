package Enities.ChanceCards;

import Controller.GameController;
import Enities.DiceCup.DiceCup;
import Enities.DiceCup.TestDie;
import Enities.GameBoard;
import Language.LanguageController;
import View.TestView;
import View.View;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GoToJailChanceCardTest {

    @Test
    void gotoJail() {
        // gotoJail is on square 3 in the test.
        DiceCup dc = new DiceCup(new TestDie[]{new TestDie(1), new TestDie(2)});
        GameBoard gb = new GameBoard(new LanguageController(), dc, "src/main/java/csv/prisonTestFields.csv");

        View view = new TestView();

        GameController gameController = new GameController(view, gb);
        gameController.resetPlayerPositions();
        // player 2 never moves.
        // turn 0 roll
        // turn 1 jail
        // turn 2 jail
        // turn 3 jail
        // turn 4 not jail
        for (int i = 0; i < 4; i++) {
            gameController.playTurn(gb.getCurrentPlayer());
            assertTrue(gb.getPlayers()[0].isJailed());
        }
        assertFalse(gb.getPlayers()[0].isJailed());

    }
}