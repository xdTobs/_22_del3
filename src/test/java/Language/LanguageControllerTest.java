package Language;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LanguageControllerTest {
    LanguageController languageController = new LanguageController();


    @Test
    @DisplayName("Get message string from LanguageController with Message class")
    void testGetMessageEnum() {
        Message buyProperty = Message.buyField("Henrik Zenkert", "Strøget");
        String expected = "Henrik Zenkert have landed on Strøget. Would you like to buy it?";
        var result = languageController.getMessage(buyProperty);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Message contains arguments.")
    void testGetMessage() {
        Message moveToMessage = Message.of(Message.Type.MOVE_TO, "Mount Doom!");
        String expected = "Move to Mount Doom!";
        var result = languageController.getMessage(moveToMessage);
        assertEquals(expected, result);
    }
}