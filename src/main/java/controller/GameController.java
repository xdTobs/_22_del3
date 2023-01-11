package controller;

import entities.chancecards.GetOutOfJailChanceCard;
import entities.fields.Field;
import entities.GameBoard;
import entities.Player;
import language.Message;
import view.GuiView;


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

        Field[] fields = GameBoard.getDefaultFields();
        GuiView guiView = GuiView.setup(fields);
        UserIO userIO = new UserIO(guiView);
        int playerCount = userIO.promptRange(Message.numberOfPlayers(), 2, 4);

        GameBoard gameBoard = GameBoard.setup(fields, userIO, playerCount);
        guiView.addPlayersToGui(gameBoard.getPlayers());
        GameController controller = new GameController(guiView, userIO, gameBoard);


        gameBoard.createPlayers(playerCount);

        return controller;
    }


    public void playGame() {
        // Moves all player to the start position.
        resetPlayerPositions();
        while (true) {
            if (gameBoard.isGameover()) {


//                basicUserIO.showMessage(gameBoard.findLoser() + gameBoard.getMessage("gameLostMsg"));
//                basicUserIO.showMessage(gameBoard.findWinner() + gameBoard.getMessage("gameWonMsg"));
                break;
            } else {
                Player currentPlayer = gameBoard.getCurrentPlayer();
                if (!currentPlayer.getHasLost()) {
                    playTurn(currentPlayer);
                }
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
        if (currentPlayer.isJailed() && currentPlayer.getGetOutOfJailCards() > 0) {
            currentPlayer.setJailed(false);
            currentPlayer.decrementGetOutOfJailCards();

        }

        if (currentPlayer.isJailed()) {
            currentPlayer.addToJailedCounter();
            int jailedCounter = currentPlayer.getJailedCounter();
            if (jailedCounter == 3) {
                currentPlayer.setJailed(false);
                currentPlayer.setJailedCounter(0);
            } else if (currentPlayer.getBalance() >= 1000) {
                String playerName = gameBoard.getCurrentPlayer().getName();
                boolean wantsToBailOut = userIO.promptYesOrNo(Message.bailOut(playerName));

                if (wantsToBailOut) {
                    currentPlayer.setJailed(false);
                    currentPlayer.setJailedCounter(0);
                    currentPlayer.setBalance(currentPlayer.getBalance() - 1000);
                }
            }
            // LEAVE JAIL MESSAGE
        } else {
            gameBoard.getDiceCup().roll();
            boolean hasPassedStart = gameBoard.movePlayer();
            view.movePlayerVisually(currentPlayer, gameBoard.getDiceCup());
            String playerName = gameBoard.getCurrentPlayer().getName();
            userIO.showMessage(Message.rollDice(playerName));
            view.update(gameBoard.getPlayers(), gameBoard.getFields(), gameBoard.getDiceCup());
            gameBoard.fieldAction(currentPlayer);
            gameBoard.getActualFields().buyHouseProcess();

            boolean playerHasBeenRemoved = gameBoard.removeBankruptPlayers();
            if (playerHasBeenRemoved) {
                userIO.showMessage(Message.remainingPlayers(gameBoard.getPlayers()));
            }
            view.update(gameBoard.getPlayers(), gameBoard.getFields(), gameBoard.getDiceCup());

            if (gameBoard.isGameover()) return;
            if (hasPassedStart) {
                userIO.showMessage(Message.passedStart(playerName));
            }
            // Checks if player gets an extra turn
            // TODO Should you get extra turn if you land on goToJail?
            // TODO If you have getOutOfJailCard and roll equal dice
            // TODO should you get to continue playing?

            if (gameBoard.getDiceCup().diceAreEqual()) {
                userIO.showMessage(Message.extraTurn(playerName));
                playTurn(currentPlayer);
            }
        }
    }
}



