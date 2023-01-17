package language;


import exceptions.NotImplementedYetException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class LanguageHandler {
    private final EnumMap<Message.Type, String> languageMap;

    public LanguageHandler(EnumMap<Message.Type, String> languageMap) {
        this.languageMap = languageMap;
    }

    public static LanguageHandler getDefaultLanguageHandler() {
        return getLanguageHandler(Language.ENGLISH);
    }

    public static LanguageHandler getLanguageHandler(Language language) {
        return switch (language) {
            case ENGLISH -> new LanguageHandler(createDictionary("messages/english.txt"));
            case SWEDISH -> new LanguageHandler(createDictionary("messages/swedish.txt"));
            default -> throw new NotImplementedYetException("Unexpected value: " + language);
        };
    }

    private static EnumMap<Message.Type, String> createDictionary(String resource) {
        InputStream inputStream = LanguageHandler.class.getClassLoader().getResourceAsStream(resource);
        if (inputStream == null) {
            throw new NullPointerException("InputStream should not be null");
        }
        var bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        // If we need to split text in englishMessages we will use * for split.
        EnumMap<Message.Type, String> dictionary = new EnumMap<>(Message.Type.class);
        for (String line : bufferedReader.lines().toList()) {
            // We use limit, so we only split on first ":"
            String[] split = line.split(":", 2);
            Message.Type type = Message.Type.valueOf(split[0]);
            dictionary.put(type, split[1]);
        }

        List<Message.Type> missingTypes = Arrays.stream(Message.Type.values()).filter(type -> !dictionary.containsKey(type)).toList();

        if (missingTypes.size() != 0) {
            String s = missingTypes.stream().map(Enum::toString).reduce("", (acc, type) -> acc + type);
            throw new RuntimeException("LanguageMap is missing the following keys: " + s);
        }
        if (dictionary.size() != Message.Type.values().length) {
            String s = dictionary.keySet().stream().map(Enum::toString).reduce("", (acc, type) -> acc + type);
            throw new RuntimeException("LanguageMap has too many keys: " + s);
        }
        return dictionary;
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
