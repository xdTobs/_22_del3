package Language;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LanguageControllerTest {

    @Test
    @DisplayName("the key moveTo should give Move To as a result, when in english.")
    void getMessage() {
        LanguageController lh = new LanguageController("english");
        String expected = "Move To";
        String key = "moveTo";
        var result = lh.getMessage(key);
        assertEquals(expected, result);
//        moveTo:Move To
    }
}