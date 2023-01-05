package Language;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

// We can use this class to implement different languages. For example, we can make translation variable be an array and then we can loop through it and print out the different translations, so the game is multi-lingual.
// TODO use this instead of LanguageHandler
public class LanguageController {
    private HashMap<String, String> languageMap;

    public LanguageController(String s) {
        Path filepath = Path.of("src/main/java/Language/" + s + "FnVarNames.txt");
        try {
            languageMap = initLanguageHandler(filepath);
        } catch (IOException e) {
            throw new RuntimeException("Could not load file: " + filepath + "\nError: " + e);
        }
    }

    public String getMessage(String key) {
        return this.languageMap.get(key);

    }

    private HashMap<String, String> initLanguageHandler(Path filepath) throws IOException {
        List<String> content = Files.readAllLines(filepath);
        languageMap = new HashMap<>();
        for (String s : content) {
            String[] keyValue = s.split(":", 2);
            if (keyValue.length > 1) {
                this.languageMap.put(keyValue[0], keyValue[1]);
            }
        }
        return languageMap;
    }

}
