package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    public static Player[] getTwoDebugPlayers(int money){
        Player player1 = new Player("Player1", money);
        Player player2 = new Player("Player2", money);
        return new Player[]{player1, player2};
    }
    Player player;
    @BeforeEach
    void setUp() {
        this.player = new Player("Test", 20);
    }
    @Test
    void isJailed() {
        assertFalse(player.isJailed());
    }

    @Test
    void setJailed() {
        player.setJailed(true);
        assertTrue(player.isJailed());
    }

    @Test
    void getBalance() {
        assertEquals(20, player.getBalance());
    }

    @Test
    void setBalance() {
        player.setBalance(30);
        assertEquals(30, player.getBalance());
    }

    @Test
    void addBalance() {
        player.addBalance(10);
        assertEquals(30, player.getBalance());
    }

    @Test
    void getPosition() {
        assertEquals(0, player.getPosition());
    }

    @Test
    void setPosition() {
        player.setPosition(10);
        assertEquals(10, player.getPosition());
    }

    @Test
    void getGetOutOfJailCards() {
        assertEquals(0, player.getGetOutOfJailCards());
    }

    @Test
    void setGetOutOfJailCards() {
        player.setGetOutOfJailCards(1);
        assertEquals(1, player.getGetOutOfJailCards());
    }

    @Test
    void getName() {
        assertEquals("Test", player.getName());
    }

    @Test dontBuyStreet() {
        player.setPosition(1);
        boolean buyField = false;
        player.getHouses() {assertEquals( 0, );
    }


}