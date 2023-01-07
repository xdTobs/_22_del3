package Language;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LanguageControllerTest {
    LanguageController languageController = new LanguageController();

    @Test
    @DisplayName("the key moveTo should give Move To as a result, when in english.")
    void getMessage() {
        String expected = "Move To";
        String key = "moveTo";
        var result = languageController.getMessage(key);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Get message with Message.Type enum")
    void testGetMessageEnum() {
        Message.Type moveToType = Message.Type.MOVE_TO;
        String expected = "Move to";
        var result = languageController.getMessage(moveToType);
        assertEquals(expected, result);
    }
}