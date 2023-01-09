package Enities;

import Controller.TestUserIO;
import Controller.UserIO;
import Enities.ChanceCards.Deck;
import Enities.DiceCup.DiceCup;
import Enities.DiceCup.TestDie;
import Enities.Fields.Field;
import View.TestView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameBoardTest {


//    public static GameBoard debugSetup(int diceValue, Field[] fields) {
//        return debugSetup(diceValue, fields, new Deck(new ArrayList<>()));
//    }
//
//    public static GameBoard debugSetup(int diceValue, Field[] fields, Deck deck) {
//    }

    GameBoard board;

    @BeforeEach
    void setUp() {
        GameBoard board = new GameBoard(GameBoard.getDefaultFields(), new UserIO(new TestView()), 2);
    }

    @Test
    void getField() {
        assertEquals("Start", board.getField(0).getName());
        assertEquals("Prøv Lykken", board.getField(2).getName());
    }

    @Test
    void fieldAction() {
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

    }

    @Test
    void getLatestChanceCard() {
        /*board.getDeck().pullCard();
        ChanceCard card = board.getDeck().getLatestChanceCard();
        assertNotNull(card != null);*/
    }


}