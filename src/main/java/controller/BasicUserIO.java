package controller;


import language.LanguageHandler;
import language.Message;

public interface BasicUserIO {
    int promptChoice(Message message, Message... choices);

    int promptRange(Message message, int min, int max);

    String promptString(Message message);

    void showMessage(Message message);

    void setLanguageHandler(LanguageHandler languageHandler);
//    public void setLanguageController(LanguageHandler languageHandler) {
//        this.languageHandler = languageHandler;
//    }
}
