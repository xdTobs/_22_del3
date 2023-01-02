package Language;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// This test needs to be rewritten if we want to implement another language.
// In the future we could refactor LanguageHandler, so we instead pass in a language to it.
class LanguageHandlerTest {
    LanguageHandler language;
    @BeforeEach
    void setUp() {

        try {
            language = new LanguageHandler();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void getFieldName() {
        int i = 20;
        String expected = "Zoo";
        String actual = LanguageHandler.getFieldName(i);
        assertEquals(expected, actual);
    }



    @Test
    void getPlayerName2() {
        String expected = "Player 2";
        String actual = language.languageMap.get("playerName2");
        assertEquals(expected, actual);
    }

    @Test
    void rollDiceMsg() {
        language.languageMap.get("language = new LanguageHandler");
        String expected = "It is your turn. Roll the Dice";
        String actual = language.languageMap.get("rollDiceMsg");
        assertEquals(expected, actual);
    }

    @Test
    void jailMsg() {
        String expected = "TEST PLAYER, you are in Jail, you lose 1$";
        String actual = language.languageMap.get("TEST PLAYER");
        assertEquals(expected, actual);
    }

    @Test
    void moveToMsg() {
        LanguageHandler language;
        try {
            language = new LanguageHandler();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String expected = "Move to";
        // TODO
        String actual = language.languageMap.get("moveTo");
        assertEquals(expected, actual);
    }

    @Test
    void chanceCardMsg() {
        String expected = "pulled a chance card. It tells you to:";
        String actual = language.languageMap.get("chanceCardMsg");
        assertEquals(expected, actual);
    }
}