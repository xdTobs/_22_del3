package language;

import exceptions.NotImplementedYetException;

public enum Language {
    ENGLISH, SWEDISH, GERMAN;

    public static Language getLanguage(String languageString) {
        return switch (languageString.toLowerCase()) {
            case "english" -> ENGLISH;
            case "swedish" -> SWEDISH;
            default -> throw new LanguageNotImplementedYetException(languageString);
        };
    }
}

class LanguageNotImplementedYetException extends NotImplementedYetException {
    public LanguageNotImplementedYetException(String language) {
        super("You have managed to pick a language that is not supported: " + language
                + "\nImpressive, but not recommended. Restart the game and pick a different language.");
    }
}