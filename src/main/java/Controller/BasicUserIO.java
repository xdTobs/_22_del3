package Controller;


import Language.Message;

public interface BasicUserIO {
    int promptChoice(Message message, Message... choices);

    int promptRange(Message message, int min, int max);

    // TODO This should probably not be string, but instead be an enum Message. Look at christians code.
    // I don't think we should send the key here. Only temp solution.
    void showMessage(Message message);
}
