package Controller;

import Language.Message;

/**
 * We use this class to make more advanced prompts for our user.
 * Instead of just having the three from the interface, we can add QOL methods here.
 */
public class UserIO implements BasicUserIO {
    BasicUserIO basicUserIO;

    public UserIO(BasicUserIO basicUserIO) {
        this.basicUserIO = basicUserIO;
    }

    @Override
    public int promptChoice(Message message, Message... choices) {
        return basicUserIO.promptChoice(message, choices);
    }

    /**
     * @param message
     * @return
     */
    public boolean promptYesOrNo(Message message) {
        return (basicUserIO.promptChoice(message, Message.yes(), Message.no())) == 0;
    }

    private void buyField(String playerName, String fieldName) {
        Message message = Message.buyField(playerName, fieldName);
        promptYesOrNo(message);
    }

    @Override
    public int promptRange(Message message, int min, int max) {
        return basicUserIO.promptRange(message, min, max);
    }

    @Override
    public void showMessage(Message message) {
        basicUserIO.showMessage(message);
    }
}
