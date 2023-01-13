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
                userIO.showMessage(Message.gameOver(gameBoard.findWinner(), gameBoard.findLosers()));
                break;
            } else {
                Player currentPlayer = gameBoard.getCurrentPlayer();
                if (currentPlayer.hasNotLost()) {
                    playTurn();
                    if (gameBoard.getDiceCup().equalDiceValue() && !currentPlayer.isJailed()) {
                        userIO.showMessage(Message.extraTurn(gameBoard.getCurrentPlayer().getName()));
                    } else {
                        gameBoard.nextPlayer();
                    }
                }
            }
        }
    }

    public void resetPlayerPositions() {
        gameBoard.resetPlayerPositions();
        view.updatePlayerLocations(gameBoard.getPlayers());
    }

    private void inJail() {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        String playerName = currentPlayer.getName();
        if (currentPlayer.getGetOutOfJailCards() > 0) {
            currentPlayer.setJailed(false);
            currentPlayer.decrementGetOutOfJailCards();
            userIO.showMessage(Message.leaveJail(currentPlayer.getName()));
        } else {
            currentPlayer.incJailTimeCounter();
            int jailedCounter = currentPlayer.getJailTimeCounter();
            boolean wantsToBailOut = false;
            if (currentPlayer.getBalance() >= 1000) {
                wantsToBailOut = userIO.promptYesOrNo(Message.bailOut(playerName));
            }
            // If player wants to bailout he can pay 1000 to bailout
            // or if he has been jailed for 3 turns he gets forced to pay bail.
            if (jailedCounter == 3 || wantsToBailOut) {
                currentPlayer.setJailed(false);
                currentPlayer.setJailTimeCounter(0);
                currentPlayer.setBalance(currentPlayer.getBalance() - 1000);
                userIO.showMessage(Message.leaveJail(playerName));
            }
            if (!wantsToBailOut) {
                userIO.showMessage(Message.rollDice(playerName));
                gameBoard.getDiceCup().roll();
                view.update(gameBoard.getPlayers(), gameBoard.getFields(), gameBoard.getDiceCup());
                /*if(gameBoard.getDiceCup().diceAreEqual()) {
                    currentPlayer.setJailed(false);
                }*/
            }
        }

    }

    private boolean rollDiceAndMove() {
        Player currentPlayer = gameBoard.getCurrentPlayer();

        userIO.showMessage(Message.rollDice(currentPlayer.getName()));
        gameBoard.getDiceCup().roll();
        view.movePlayerVisually(currentPlayer, gameBoard.getDiceCup());
        // Player is given 4000 in movePlayer if he passes start.
        boolean hasPassedStart = gameBoard.movePlayer();
        view.update(gameBoard.getPlayers(), gameBoard.getFields(), gameBoard.getDiceCup());
        return hasPassedStart;
    }

    private void checkForAndRemoveBankruptPlayers() {
        boolean playerHasBeenRemoved = gameBoard.removeBankruptPlayers();
        if (playerHasBeenRemoved) {
            userIO.showMessage(Message.remainingPlayers(gameBoard.getRemainingPlayerNames()));
        }
        view.update(gameBoard.getPlayers(), gameBoard.getFields(), gameBoard.getDiceCup());
    }

    private void playNotInJailTurn() {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        String playerName = currentPlayer.getName();

        boolean hasPassedStart = rollDiceAndMove();
        if (hasPassedStart) {
            currentPlayer.addBalance(4000);
            userIO.showMessage(Message.passedStart(playerName));
        }
        gameBoard.fieldAction();
        gameBoard.getFieldImpl().buyHouseProcess();

        checkForAndRemoveBankruptPlayers();


        // TODO Should you get extra turn if you land on goToJail?
        // TODO If you have getOutOfJailCard and roll equal dice
        // TODO should you get to continue playing?
        // Checks if player gets an extra turn
        // You don't immediately get an extra turn if you got equal dice before entering jail.
        // If you enter jail, everything else stops, and you cannot continue until next turn.
        // According to the rules you get an extra turn if you get equal dice when in jail,
        // and you move the spaces the eyes show.

    }


    public void playTurn() {
        // If a player was jailed last turn he needs to pay a fine to get out or use a get out of jail free card.
        Player currentPlayer = gameBoard.getCurrentPlayer();
        if (currentPlayer.isJailed()) {
            inJail();
        } else {
            playNotInJailTurn();
        }
        // If player was jailed last turn and has a get out of jail card he should be release, and then he will play normal turn.
    }
}



