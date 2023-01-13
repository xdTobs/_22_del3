package language;

import controller.BasicUserIO;
import org.apache.commons.lang.NotImplementedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LanguageControllerTest {
    LanguageController languageController = new LanguageController();

    BasicUserIO basicIO = new BasicUserIO() {
        @Override
        public int promptChoice(Message message, Message... choices) {
            throw new NotImplementedException();
        }

        @Override
        public int promptRange(Message message, int min, int max) {
            throw new NotImplementedException();
        }

        @Override
        public String promptString(Message message) {
            return null;
        }

        @Override
        public void showMessage(Message message) {
            throw new NotImplementedException();
        }
    };

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
        Message moveToMessage = Message.of(Message.Type.MOVE_TO, "Henrik", "Mount Doom");
        String expected = "Henrik, move to Mount Doom.";
        var result = languageController.getMessage(moveToMessage);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Message contains arguments.")
    void testBuyFieldMessage() {
        Message message = Message.buyField("Player 1", "Strøget");
    }

    @Test
    void getMessage() {
//        * pulled a chance card. It tells you to: *
        var m = Message.chanceCard("Henrik", "Go to an empty square.");
        var s = languageController.getMessage(m);
        assertEquals("Henrik pulled a chance card. It tells you to: Go to an empty square.", s);
    }
}