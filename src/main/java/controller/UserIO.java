package controller;

import language.Message;

/**
 * We use this class to make more advanced prompts for our user.
 * Instead of just having the three from the interface, we can add quality of life methods here.
 * Our GuiView implements the most basic of IO and every IO goes through the GuiView IO. We add more specific methods here,
 * which makes it easier to ask Player for input or show message.
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

    public boolean promptYesOrNo(Message message) {
        return (basicUserIO.promptChoice(message, Message.yes(), Message.no())) == 0;
    }
//    private void promptBuyField(String playerName, String fieldName) {
//        promptYesOrNo(Message.buyField(playerName, fieldName));
//    }

    @Override
    public int promptRange(Message message, int min, int max) {
        return basicUserIO.promptRange(message, min, max);
    }

    @Override
    public void showMessage(Message message) {
        basicUserIO.showMessage(message);
    }

}
