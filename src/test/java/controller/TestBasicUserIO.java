package controller;

import language.LanguageHandler;
import language.Message;

public class TestBasicUserIO implements BasicUserIO {
    @Override
    public int promptChoice(Message message, Message... choices) {
        return 0;
    }

    @Override
    public int promptRange(Message message, int min, int max) {
        return 0;
    }

    @Override
    public String promptString(Message message) {
        return null;
    }

    @Override
    public void showMessage(Message message) {

    }

    @Override
    public void setLanguageHandler(LanguageHandler languageHandler) {

    }
}
