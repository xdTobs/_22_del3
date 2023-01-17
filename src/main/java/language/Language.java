package language;

import exceptions.NotImplementedYetException;

public enum Language {
    ENGLISH, SWEDISH;

    public static Language getLanguageEnum(String languageString) {
        return switch (languageString.toLowerCase()) {
            case "english" -> ENGLISH;
            case "swedish" -> SWEDISH;
            default -> throw new LanguageNotImplementedException(languageString);
        };
    }

    public static String[] getLanguages() {
        String[] languages = new String[Language.values().length];
        for (int i = 0; i < Language.values().length; i++) {
            languages[i] = Language.values()[i].toString();
        }
        return languages;
    }
}

class LanguageNotImplementedException extends NotImplementedYetException {
    public LanguageNotImplementedException(String language) {
        super("You have managed to pick a language that is not supported: " + language
                + "\nImpressive, but not recommended. Restart the game and pick a different language.");
    }
}