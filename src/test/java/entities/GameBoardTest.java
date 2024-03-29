package entities;

import controller.UserIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.TestView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameBoardTest {


    GameBoard board;

    @BeforeEach
    void setUp() {
        Player[] players = PlayerTest.getTwoDebugPlayers(30000);
        board = new GameBoard(GameBoard.getDefaultFields(), new UserIO(new TestView()), players);
    }

    @Test
    void getField() {
        assertEquals("Start", board.getField(0).getName());
        assertEquals("Prøv lykken", board.getField(2).getName());
    }

    @Test
    void getFields() {
        assertEquals(40, board.getFields().length);
    }

    @Test
    void resetPlayerPositions() {
        board.resetPlayerPositions();
        assertEquals(0, board.getPlayers()[0].getPosition());
        assertEquals(0, board.getPlayers()[1].getPosition());
    }

    @Test
    void getCurrentPlayer() {
        assertEquals("Player1", board.getCurrentPlayer().getName());
    }

    @Test
    void getDiceCup() {
    }

    @Test
    void currentPlayerIsOnChanceField() {
        board.getCurrentPlayer().setPosition(2);
        assertTrue(board.currentPlayerIsOnChanceField());
    }

    @Test
    void rollDieMovePlayer() {
        board.movePlayer();
        assertTrue(board.getCurrentPlayer().getPosition() > 0);
    }

    @Test
    void getPlayers() {
        assertEquals(2, board.getPlayers().length);
        assertEquals("Player1", board.getPlayers()[0].getName());
        assertEquals("Player2", board.getPlayers()[1].getName());
    }

    @Test
    void nextPlayer() {
        board.nextPlayer();
        assertEquals("Player2", board.getCurrentPlayer().getName());
    }

    @Test
    void isGameover() {
        var currentPlayer = board.getCurrentPlayer();
        currentPlayer.setBalance(-10);
        board.removeBankruptPlayers();
        assertTrue(board.isGameover());
    }


}