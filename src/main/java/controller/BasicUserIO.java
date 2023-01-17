package controller;


import language.LanguageHandler;
import language.Message;

public abstract class BasicUserIO {
    LanguageHandler languageHandler;

    public abstract int promptChoice(Message message, Message... choices);

    public abstract int promptRange(Message message, int min, int max);

    public abstract String promptString(Message message);

    public abstract void showMessage(Message message);

    public void setLanguageController(LanguageHandler languageHandler) {
        this.languageHandler = languageHandler;
    }
}
