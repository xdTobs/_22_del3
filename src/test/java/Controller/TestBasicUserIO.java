package Controller;

import Language.Message;

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
    public void showMessage(Message message) {

    }
}
