package Enities.Fields;

import Enities.GameBoard;
import Enities.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StreetTest {

    Street street1 = new Street("4");
    Street street2 = new Street("5");

    @Test
    void getName() {
        assertEquals("Slikbutikken", street1.getName());
        assertEquals("Iskiosken", street2.getName());
    }

    @Test
    void executeFieldAction() {
    }

    @Test
    void getRent() {
        assertEquals(1, street1.getRent(0));
        assertEquals(1, street2.getRent(0));
    }

    @Test
    void getOwner() {
        assertEquals("Bank", street1.getOwner());
        assertEquals("Bank", street2.getOwner());
    }

    @Test
    void setOwner() {
        street1.setOwnerName("Player 1");
        street2.setOwnerName("Player 2");
        assertEquals("Player 1", street1.getOwner());
        assertEquals("Player 2", street2.getOwner());
    }

    @Test
    void findPositionOfPairStreet() {
        int position1 = street1.getPositionOfPairStreet();
        assertEquals(5, position1);
        int position2 = street2.getPositionOfPairStreet();
        assertEquals(4, position2);
    }

    @Test
    void doubleRentForPairStreets() {
        GameBoard gameBoard = new GameBoard();
        gameBoard.createPlayers(4);
        Street s1 = (Street) gameBoard.getField(4);
        Street s2 = (Street) gameBoard.getField(5);
        s1.setOwnerName("Player4");
        s2.setOwnerName("Player4");
        Player player1 = gameBoard.getCurrentPlayer();
        player1.setPosition(4);
        s1.executeFieldAction(gameBoard);
        assertEquals(14, player1.getBalance());
    }
}