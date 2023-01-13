package controller;

import entities.GameBoard;
import entities.Player;
import language.Message;


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
     * We use this to create GameController, so we can easier test the GameController with custom values and setup.
     * If we have setup logic in constructor we can't create a controller for testing with completely custom settings
     */
    public static GameController setup(View view, UserIO userIO, GameBoard gameBoard) {
        view.addPlayersToGui(gameBoard.getPlayers());
        return new GameController(view, userIO, gameBoard);
    }


    public void playGame() {
        // Moves all player to the start position.
        resetPlayerPositions();
        while (true) {
            if (gameBoard.isGameover()) {
                // TODO
                userIO.showMessage(Message.gameOver(gameBoard.findWinner(), gameBoard.findLosers()));
                break;
            } else {
                Player currentPlayer = gameBoard.getCurrentPlayer();
                if (currentPlayer.hasNotLost()) {
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

        String playerName = gameBoard.getCurrentPlayer().getName();

        // If player was jailed last turn and has a get out of jail card he should be release and then he will play normal turn.
        if (currentPlayer.isJailed() && currentPlayer.getGetOutOfJailCards() > 0) {
            currentPlayer.setJailed(false);
            currentPlayer.decrementGetOutOfJailCards();
            userIO.showMessage(Message.leaveJail(currentPlayer.getName()));
        }

        if (currentPlayer.isJailed()) {
            currentPlayer.addToJailedCounter();
            int jailedCounter = currentPlayer.getJailedCounter();
            boolean wantsToBailOut = false;
            if (currentPlayer.getBalance() >= 1000) {
                wantsToBailOut = userIO.promptYesOrNo(Message.bailOut(playerName));
            }
            // If player wants to bailout he can pay 1000 to bailout or if he has been jailed for 3 turns he gets forced to pay bail.
            if (jailedCounter == 3 || wantsToBailOut) {
                currentPlayer.setJailed(false);
                currentPlayer.setJailedCounter(0);
                currentPlayer.setBalance(currentPlayer.getBalance() - 1000);
                userIO.showMessage(Message.leaveJail(playerName));
            }
        } if (!currentPlayer.isJailed()) {
            userIO.showMessage(Message.rollDice(playerName));
            gameBoard.getDiceCup().roll();
            view.movePlayerVisually(currentPlayer, gameBoard.getDiceCup());
            boolean hasPassedStart = gameBoard.movePlayer();
            view.update(gameBoard.getPlayers(), gameBoard.getFields(), gameBoard.getDiceCup());
            gameBoard.fieldAction(currentPlayer);
            gameBoard.getFieldImpl().buyHouseProcess();

            boolean playerHasBeenRemoved = gameBoard.removeBankruptPlayers();
            if (playerHasBeenRemoved) {
                userIO.showMessage(Message.remainingPlayers(gameBoard.getRemainingPlayerNames()));
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



