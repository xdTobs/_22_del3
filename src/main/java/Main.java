import controller.GameController;

public class Main {

    /**
     * The entry point of application.
     */
    public static void main(String[] args) {
        GameController game = GameController.setup();

        game.playGame();

    }
}
