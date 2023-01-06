package Enities.Fields;

import Controller.GameController;
import Controller.UserIO;
import Enities.DiceCup.DiceCup;
import Enities.DiceCup.TestDie;
import Enities.GameBoard;
import Language.LanguageController;
import View.TestView;
import Controller.View;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GoToJailTest {

    @Test
    void gotoJail() {
        // gotoJail is on square 3 in the test.
        DiceCup dc = new DiceCup(new TestDie[]{new TestDie(1), new TestDie(2)});
        GameBoard gameBoard = new GameBoard(new LanguageController(), dc, "src/main/java/csv/prisonTestFields.csv");
        UserIO userIO = new UserIO() {
            @Override
            public int promptChoices(String message, String[] choices) {
                if(message == "yes"){

                }

                return 0;
            }

            @Override
            public int promptRange(String msg, int min, int max) {
                throw new AssertionError("Prompt range should not be called.");

            }
        }
        View view = new TestView();

        GameController gameController = new GameController(view, userIO,gameBoard);
        gameController.resetPlayerPositions();
        // player 2 never moves.
        // turn 0 roll
        // turn 1 jail
        // turn 2 jail
        // turn 3 jail
        // turn 4 not jail
        gameController.playTurn(gameBoard.getCurrentPlayer());
        for (int i = 0; i < 3; i++) {
            assertTrue(gameBoard.getCurrentPlayer().isJailed());
            gameController.playTurn(gameBoard.getCurrentPlayer());
        }
        assertFalse(gameBoard.getCurrentPlayer().isJailed());
    }
}