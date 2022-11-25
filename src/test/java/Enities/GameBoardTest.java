package Enities;

import Enities.ChanceCards.ChanceCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {

    GameBoard board = new GameBoard();

    @BeforeEach
    void setUp() {
        this.board = new GameBoard();
        board.createPlayers(2);
    }

    @Test
    void getField() {
        assertEquals("Start", board.getField(0).getName());
        assertEquals("Parkering", board.getField(12).getName());
    }

    @Test
    void fieldAction() {
    }

    @Test
    void getFields() {
        assertEquals(24, board.getFields().length);
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
        board.getCurrentPlayer().setPosition(3);
        assertTrue(board.currentPlayerIsOnChanceField());
    }

    @Test
    void rollDieMovePlayer() {
        board.rollDieMovePlayer();
        assertTrue(board.getCurrentPlayer().getPosition() > 0);
    }

    @Test
    void payFine() {
        board.getCurrentPlayer().setJailed(true);
        board.payFine(board.getCurrentPlayer());
        assertFalse(board.getCurrentPlayer().isJailed());
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
        board.getPlayers()[0].setBalance(0);
        assertTrue(board.isGameover());
    }

    @Test
    void getLatestChanceCard() {
        board.getDeck().pullCard();
        ChanceCard card = board.getDeck().getLatestChanceCard();
        assertNotNull(card != null);
    }

    @Test
    void getStreet() {
        assertEquals("Burgerbaren", board.getStreet(1).getName());
        assertEquals("Pizzeriaet", board.getStreet(2).getName());
    }
}