package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {
    public static Player[] getTwoDebugPlayers(int money) {
        Player player1 = new Player("Player1", money);
        Player player2 = new Player("Player2", money);
        return new Player[]{player1, player2};
    }

    Player player;

    @BeforeEach
    void setUp() {
        this.player = new Player("Test", 20);
    }


    // EXAMPLE
    // TestID: 21.
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
    void addNegativeBalance() {
        player.addBalance(-10);
        assertEquals(10, player.getBalance());
    }

    @Test
    @DisplayName("Adding balance to a player who has lost should not change the balance.")
    void addBalanceToPlayerLost() {
        player.setHasLost(true);
        player.addBalance(10);
        assertEquals(20, player.getBalance());
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
        assertFalse(player.hasGetOutOfJailCard());
    }

    @Test
    void setGetOutOfJailCards() {
        player.setGetOutOfJailCards(1);
        assertTrue(player.hasGetOutOfJailCard());
    }

    @Test
    void getName() {
        assertEquals("Test", player.getName());
    }

    @Test
    void playersStartWith30000() {
        Player player = new Player("Test Player");
        assertEquals(30000, player.getBalance());
    }

}