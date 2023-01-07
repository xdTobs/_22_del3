package Controller;


public interface UserIO {
    int promptChoices(String message, String... choices);

    int promptRange(String msg, int min, int max);

    // TODO This should probably not be string, but instead be an enum Message. Look at christians code.
    // I don't think we should send the key here. Only temp solution.
    void showMessage(String messageKey, String... args);
}
