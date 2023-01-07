package Language;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

// We can use this class to implement different languages. For example, we can make translation variable be an array and then we can loop through it and print out the different translations, so the game is multi-lingual.
// TODO use this instead of LanguageHandler
public class LanguageController {
    private EnumMap<Message.Type, String> languageMap = new EnumMap<>(Message.Type.class);

    public LanguageController() {
        this("englishMessages.txt");
    }

    public LanguageController(String resource) {
        InputStream inputStream = LanguageController.class.getClassLoader().getResourceAsStream(resource);
        if (inputStream == null) {
            throw new NullPointerException("InputStream should not be null");
        }
        var bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        // If we need to split text in englishMessages we will use $ for split.
        // I don't think we will need that.
        for (String line : bufferedReader.lines().toList()) {
            String[] split = line.split(":");
            Message.Type type = Message.Type.valueOf(split[0]);
            languageMap.put(type, split[1]);
        }

        List<Message.Type> missingTypes = Arrays.stream(Message.Type.values())
                .filter(type -> !languageMap.containsKey(type))
                .toList();

        if (missingTypes.size() != 0) {
            String s = missingTypes.stream().map(type -> type.toString()).reduce("", (acc, type) -> acc + type);
            throw new UnsupportedOperationException("LanguageMap is missing the following keys:\n" + s);
        }
    }

    public String getMessage(String key) {
        return this.languageMap.get(key);
    }

    public String getMessage(Message.Type type) {
        return languageMap.get(type);
    }

}
