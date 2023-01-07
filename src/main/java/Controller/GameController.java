package Controller;

import Enities.ChanceCardImpl;
import Enities.FieldImpl;
import Enities.GameBoard;
import Enities.Player;
import View.GuiView;


/**
 * The controller.
 */
public class GameController {
    final private View view;
    final private GameBoard gameBoard;
    private final UserIO userIO;


    /**
     * Instantiates a new Game controller.
     *
     * @param view      the view
     * @param gameBoard the gameBoard, our model
     */
    public GameController(View view, UserIO userIO, GameBoard gameBoard) {
        this.view = view;
        this.gameBoard = gameBoard;
        this.userIO = userIO;
    }

    /**
     * We use this to create GameController so we can easier test the GameController with custom values and setup.
     * If we have setup logic in constructor we can't create a controller for testing with completely custom settings
     */
    public static GameController setup() {
        GameBoard gameBoard = GameBoard.setup("fields.csv");
        GuiView guiView = GuiView.setup(gameBoard.getFields());
        GameController controller = new GameController(guiView, guiView, gameBoard);

        String key = "PLAYERCOUNTMSG";
        int playerCount = guiView.promptRange(key, 2, 4);
        gameBoard.createPlayers(playerCount);
        guiView.addPlayersToGui(gameBoard.getPlayers());

        gameBoard.setActualFields(new FieldImpl(gameBoard, guiView));
        gameBoard.setActualChanceCard(new ChanceCardImpl(gameBoard, guiView));

        return controller;
    }


    public void playGame() {
        // Moves all player to the start position.
        resetPlayerPositions();
        while (true) {
            if (gameBoard.isGameover()) {
                userIO.showMessage(gameBoard.findLoser() + gameBoard.getMessage("gameLostMsg"));
                userIO.showMessage(gameBoard.findWinner() + gameBoard.getMessage("gameWonMsg"));
                break;

            } else {
                Player currentPlayer = gameBoard.getCurrentPlayer();
                playTurn(currentPlayer);
                gameBoard.nextPlayer();
            }
        }
    }

    public void resetPlayerPositions() {
        gameBoard.resetPlayerPositions();
        view.updatePlayerLocations(gameBoard.getPlayers());
    }


    public void playTurn(Player currentPlayer) {
        // If a player was jailed last turn he needs to pay a fine to get out or use a get out of jail free card.
        if (currentPlayer.isJailed()) {
            currentPlayer.addToJailedCounter();
            int jailedCounter = currentPlayer.getJailedCounter();
            if (jailedCounter == 3) {
                currentPlayer.setJailed(false);
                currentPlayer.setJailedCounter(0);
            } else if (currentPlayer.getBalance() >= 1000) {
                String[] choices = new String[]{gameBoard.getMessage("yes"), gameBoard.getMessage("no")};
                String yesOrNo = view.promptPlayer(choices, gameBoard.getCurrentPlayer().getName() + gameBoard.getMessage("wantToBailOut"));
                if (yesOrNo.equals(gameBoard.getMessage("yes"))) {
                    currentPlayer.setJailed(false);
                    currentPlayer.setJailedCounter(0);
                    currentPlayer.setBalance(currentPlayer.getBalance() - 1000);
                }
            }
        }
        if (!currentPlayer.isJailed()) {
            boolean hasPassedStart = gameBoard.rollDieMovePlayer();
            userIO.showMessage(currentPlayer.getName() + " " + gameBoard.getMessage("rollDiceMsg"));
            view.update(gameBoard.getPlayers(), gameBoard.getFields());
            gameBoard.fieldAction(currentPlayer);
            //gameBoard.isPlayerBankrupt();
            view.update(gameBoard.getPlayers(), gameBoard.getFields());

            if (hasPassedStart) {
                userIO.showMessage(gameBoard.getMessage("passedStartMsg"));
            }
            // Checks if player gets an extra turn
            // TODO Should you get extra turn if you land on goToJail?

            if (gameBoard.getDiceCup().diceAreEqual()) {
                userIO.showMessage(currentPlayer.getName() + gameBoard.getMessage("extraTurn"));
                playTurn(currentPlayer);
            }
        }
    }
}



