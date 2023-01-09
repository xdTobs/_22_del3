import Controller.GameController;

public class Main {

    /**
     * The entry point of application.
     */
    public static void main(String[] args) {
//        Field[] fields = GameBoard.getDefaultFields();
//        GuiView guiView = GuiView.setup(fields);
//        UserIO userIO = new UserIO(guiView);
//        GameBoard gameBoard = GameBoard.setup(fields, userIO);

        GameController game = GameController.setup();

        game.playGame();

    }
}
