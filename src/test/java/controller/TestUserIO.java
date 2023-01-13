package controller;

import language.Message;

public class TestUserIO extends UserIO {

    public static TestUserIO debugSetup() {
        BasicUserIO basicUserIO = new controller.TestBasicUserIO();
        return new TestUserIO(basicUserIO);
    }

    public TestUserIO(BasicUserIO basicUserIO) {
        super(basicUserIO);
    }

    @Override
    public int promptChoice(Message message, Message... choices) {
        return 1;
    }

    @Override
    public boolean promptYesOrNo(Message message) {
        return true;
    }

    @Override
    public int promptRange(Message message, int min, int max) {
        return 2;
    }

    @Override
    public void showMessage(Message message) {
//        System.out.println("Message type: " + message.getType().name());
    }

}

