package Controller;

public interface UserIO {
    int promptChoices(String message, String[] choices);

    int promptRange(String msg, int min, int max);

    void showMessage(String leaveJailMsg);
    // TODO Message
}
