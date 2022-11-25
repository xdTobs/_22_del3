package Language;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

// This test needs to be rewritten if we want to implement another language.
// In the future we could refactor LanguageHandler, so we instead pass in a language to it.
class LanguageHandlerTest {

    @Test
    void getFieldName() {
        int i = 20;
        String expected = "Zoo";
        String actual = LanguageHandler.getFieldName(i);
        assertEquals(expected, actual);
    }

    @Test
    void gameWonMsg() {
        String expected = " has won the game!";
        String actual = LanguageHandler.gameWonMsg();
        assertEquals(expected, actual);
    }

    @Test
    void getPlayerName1() {
        String expected = "Player 1";
        String actual = LanguageHandler.getPlayerName1();
        assertEquals(expected, actual);
    }

    @Test
    void getPlayerName2() {
        String expected = "Player 2";
        String actual = LanguageHandler.getPlayerName2();
        assertEquals(expected, actual);
    }

    @Test
    void rollDiceMsg() {
        String expected = "It is your turn. Roll the Dice";
        String actual = LanguageHandler.rollDiceMsg();
        assertEquals(expected, actual);
    }

    @Test
    void jailMsg() {
        String expected = "TEST PLAYER, you are in Jail, you lose 1$";
        String actual = LanguageHandler.jailMsg("TEST PLAYER");
        assertEquals(expected, actual);
    }

    @Test
    void moveToMsg() {
        String expected = "Move to";
        // TODO
        String actual = LanguageHandler.moveToMsg(1);
        assertEquals(expected, actual);
    }

    @Test
    void chanceCardMsg() {
        String expected = "pulled a chance card. It tells you to:";
        String actual = LanguageHandler.chanceCardMsg();
        assertEquals(expected, actual);
    }
}