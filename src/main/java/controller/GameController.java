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
                boolean wantToQuit = userIO.promptYesOrNo(Message.gameOver(gameBoard.findWinner(), gameBoard.findLosers()));
                if (wantToQuit) {
                    break;
                }
            } else {
                Player currentPlayer = gameBoard.getCurrentPlayer();
                if (currentPlayer.hasNotLost()) {
                    playTurn();
                    if (gameBoard.getDiceCup().equalDiceValue() && !currentPlayer.isJailed()) {
                        userIO.showMessage(Message.extraTurn(gameBoard.getCurrentPlayer().getName()));
                    } else {
                        gameBoard.nextPlayer();
                        while (gameBoard.getCurrentPlayer().isBankrupt()) {
                            gameBoard.nextPlayer();
                        }
                    }
                }

            }
        }
    }

    public void resetPlayerPositions() {
        gameBoard.resetPlayerPositions();
        view.updatePlayerLocations(gameBoard.getPlayers());
    }

    private void leaveJail() {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        currentPlayer.setJailed(false);
        currentPlayer.setJailTimeCounter(0);
    }

    private void inJail() {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        String playerName = currentPlayer.getName();
        boolean shouldLeaveJail = false;
        if (currentPlayer.hasGetOutOfJailCard()) {
            shouldLeaveJail = true;
            currentPlayer.decrementGetOutOfJailCards();
            userIO.showMessage(Message.leaveJail(currentPlayer.getName()));
        } else {
            currentPlayer.incJailTimeCounter();
            // If player wants to bailout he can pay 1000 to bailout
            // or if he has been jailed for 3 turns he gets forced to pay bail.
            if (currentPlayer.getJailTimeCounter() == 3 || (currentPlayer.getBalance() >= 1000 && userIO.promptYesOrNo(Message.bailOut(playerName)))) {
                currentPlayer.addBalance(-1000);
                shouldLeaveJail = true;
            } else {
                userIO.showMessage(Message.rollDice(playerName));
                gameBoard.getDiceCup().roll();
                view.updateDie(gameBoard.getDiceCup());
                // If player leaves jail he gets to roll again and move as a normal turn.
                if (gameBoard.getDiceCup().equalDiceValue()) {
                    shouldLeaveJail = true;
                }
            }
        }
        if (shouldLeaveJail) {
            userIO.showMessage(Message.leaveJail(playerName));
            leaveJail();
        }
    }

    private boolean rollDiceAndMove() {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        userIO.showMessage(Message.rollDice(currentPlayer.getName()));
        gameBoard.getDiceCup().roll();
        view.updateDie(gameBoard.getDiceCup());
        view.movePlayerVisually(currentPlayer, gameBoard.getDiceCup());

        // Player is given 4000 in movePlayer if he passes start.
        boolean hasPassedStart = gameBoard.movePlayer();
        view.updatePlayerBalances(gameBoard.getPlayers());
        view.updatePlayerLocations(gameBoard.getPlayers());
        view.updateHousesAndFields(gameBoard.getFields());
        //view.update(gameBoard.getPlayers(), gameBoard.getFields(), gameBoard.getDiceCup());
        return hasPassedStart;
    }

    private void checkForAndRemoveBankruptPlayers() {
        boolean playerHasBeenRemoved = gameBoard.removeBankruptPlayers();
        if (playerHasBeenRemoved && !gameBoard.isGameover()) {
            userIO.showMessage(Message.remainingPlayers(gameBoard.getRemainingPlayerNames()));
        }
        view.updatePlayerBalances(gameBoard.getPlayers());
        view.updatePlayerLocations(gameBoard.getPlayers());
        view.updateHousesAndFields(gameBoard.getFields());
        //view.update(gameBoard.getPlayers(), gameBoard.getFields(), gameBoard.getDiceCup());
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
        gameBoard.getFieldImpl().sellHousePawnProcess();
        gameBoard.getFieldImpl().buyPawnedFields();

        checkForAndRemoveBankruptPlayers();





    }


    /**
     * Checks if player is jailed or not, and then pays the turn accordingly
     */
    public void playTurn() {
        // If a player was jailed last turn he needs to pay a fine to get out or use a get out of jail free card.
        Player currentPlayer = gameBoard.getCurrentPlayer();
        if (currentPlayer.isJailed()) {
            inJail();
        }
        if (!currentPlayer.isJailed()){
            playNotInJailTurn();
        }
        // If player was jailed last turn and has a get out of jail card he should be release, and then he will play normal turn.
    }
}



