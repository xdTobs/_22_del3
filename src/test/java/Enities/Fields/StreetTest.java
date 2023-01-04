package Enities.Fields;

import Enities.GameBoard;
import Enities.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StreetTest {

    Street street1 = new Street("Bülowsvej,13, street,2800,2000,200,1000,3000,9000,12500,15000");
    Street street2 = new Street("Gl. Kongevej,14, street,3200,2000,250,1250,3750,10000,14000,18000");

    @Test
    void getName() {
        assertEquals("Bülowsvej", street1.getName());
        assertEquals("Gl. Kongevej", street2.getName());
    }

    @Test
    void executeFieldAction() {
    }

    @Test
    void getRent() {
        assertEquals(200, street1.getRent(0));
        assertEquals(250, street2.getRent(0));
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

    /*@Test
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
    }*/
}