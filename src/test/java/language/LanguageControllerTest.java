package language;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LanguageControllerTest {
    LanguageController languageController = LanguageController.getDefaultLanguageController();


    @Test
    @DisplayName("Get message string from LanguageController with Message class")
    void testGetMessageEnum() {
        Message buyProperty = Message.buyField("Henrik Zenkert", "Strøget");
        String expected = "Henrik Zenkert has landed on Strøget. Would you like to buy it?";
        var result = languageController.getMessage(buyProperty);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Message contains arguments.")
    void testBuyFieldMessage() {
        Message message = Message.buyField("Player 1", "Strøget");
        assertArrayEquals(new String[]{"Player 1", "Strøget"}, message.getArgs());
    }

    @Test
    void getMessage() {
        // * pulled a chance card. It tells you to: *
        var m = Message.chanceCard("Henrik", "Go to an empty square.");
        var s = languageController.getMessage(m);
        assertEquals("Henrik pulled a chance card. It tells you to: Go to an empty square.", s);
    }
}