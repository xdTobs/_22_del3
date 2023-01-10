package language;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Pattern;

// We can use this class to implement different languages. For example, we can make translation variable be an array and then we can loop through it and print out the different translations, so the game is multi-lingual.
// TODO use this instead of LanguageHandler
public class LanguageController {
    private final EnumMap<Message.Type, String> languageMap = new EnumMap<>(Message.Type.class);

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
            String[] split = line.split(":", 2);
            Message.Type type = Message.Type.valueOf(split[0]);
            languageMap.put(type, split[1]);
        }

        List<Message.Type> missingTypes = Arrays.stream(Message.Type.values()).filter(type -> !languageMap.containsKey(type)).toList();

        if (missingTypes.size() != 0) {
            String s = missingTypes.stream().map(Enum::toString).reduce("", (acc, type) -> acc + type);
            throw new UnsupportedOperationException("LanguageMap is missing the following keys:\n" + s);
        }
    }

    // First arg in message is prepended to message rest of args are appended seperated by ","
    public String getMessage(Message message) {
        Iterator<String> args = Arrays.stream(message.getArgs()).iterator();
        String[] chars = languageMap.get(message.getType()).split(Pattern.quote(""));
        for (int i = 0; i < chars.length; i++) {
            if (chars[i].equals("*")) {
                chars[i] = args.next();
            }
        }
        if (args.hasNext()) {
            StringBuilder argsLeft = new StringBuilder();
            String s = languageMap.get(message.getType());
            while (args.hasNext()) {
                argsLeft.append(args.next());
            }
            throw new IllegalArgumentException("You have more arguments for your message than it uses.\nMessage:" + s + "\nargs left: " + argsLeft);
        }
        return String.join("", chars);

//
//        StringBuilder sb = new StringBuilder();
//        if (args.hasNext()) {
//            sb.append(args.next());
//        }
//        sb.append(languageMap.get(message.getType()));
//
//        while (args.hasNext()) {
//            sb.append(" " + args.next());
//        }
//        return sb.toString();
    }

}