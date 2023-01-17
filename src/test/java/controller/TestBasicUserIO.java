package controller;

import language.Message;

public class TestBasicUserIO extends BasicUserIO {
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
}
