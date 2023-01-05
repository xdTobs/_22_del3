package Language;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LanguageHandlerTest {

    @Test
    @DisplayName("the key moveTo should give Move To as a result, when in english.")
    void getMessage() {
        try {
            var l = new LanguageHandler();
            String expected = "Move To";
            String key = "moveTo";
            var result = l.languageMap.get(key);
            assertEquals(expected, result);
        } catch (IOException e) {
            throw new RuntimeException("Could not load file.\nError: " + e);
        }
    }
}