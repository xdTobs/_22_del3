package Controller;

import Language.Message;

public interface UserIO {
    int promptChoices(String message, String[] choices);

    int promptRange(String msg, int min, int max);

    void showMessage(Message.Type leaveJailMsg);
    // TODO Message
}
