package Language;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

// We can use this class to implement different languages. For example, we can make translation variable be an array and then we can loop through it and print out the different translations, so the game is multi-lingual.
// TODO use this instead of LanguageHandler
public class LanguageHandlerStatic {
    private final static HashMap<String, String> languageMap = new HashMap<>();

    public static String getMessage(String key) {
        if (languageMap.isEmpty()) {
            try {
                initLanguageHandler();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return languageMap.get(key);
    }

    private static void initLanguageHandler() throws IOException {
        List<String> content = Files.readAllLines(Path.of("src/main/java/Language/LanguageEnglish"));
        for (String s : content) {
            String[] keyValue = s.split(":", 2);
            if (keyValue.length > 1) {
                languageMap.put(keyValue[0], keyValue[1]);
            }
        }
    }

}
