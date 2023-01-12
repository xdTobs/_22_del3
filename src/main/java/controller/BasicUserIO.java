package controller;


import language.Message;

public interface BasicUserIO {
    int promptChoice(Message message, Message... choices);

    int promptRange(Message message, int min, int max);

    String promptString(Message message);

    void showMessage(Message message);
}
