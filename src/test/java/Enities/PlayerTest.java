package Enities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

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
        assertEquals(30000, player.getBalance());
    }

    @Test
    void setBalance() {
        player.setBalance(30000);
        assertEquals(30000, player.getBalance());
    }

    @Test
    void addBalance() {
        player.addBalance(4000);
        assertEquals(4000, player.getBalance());
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
}

