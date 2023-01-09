package Controller;

import Enities.ActualChanceCard;
import Enities.ActualFields;
import Enities.GameBoard;
import Enities.Player;
import View.*;


/**
 * The controller.
 */
public class GameController {
    final private View view;
    final private GameBoard gameBoard;
    private ActualChanceCard acc;

    /**
     * Instantiates a new Game controller.
     *
     * @param view      the view
     * @param gameBoard the gameBoard, our model
     */
    public GameController(View view, GameBoard gameBoard) {
        this.view = view;
        this.gameBoard = gameBoard;
        int playerCount = view.promptPlayerCount(gameBoard.getMessage("playerCountMsg"), 2, 4);
        gameBoard.createPlayers(playerCount);
        view.addPlayersToGui(gameBoard.getPlayers());
        acc = new ActualChanceCard(gameBoard, view);
        gameBoard.setAcc(acc);
        gameBoard.setActualFields(new ActualFields(gameBoard, view));
    }

    public void playGame() {
        // Moves all player to the start position.
        resetPlayerPositions();
        while (true) {
            if (gameBoard.isGameover()) {
                view.showMessage(gameBoard.findLoser() + gameBoard.getMessage("gameLostMsg"));
                view.showMessage(gameBoard.findWinner() + gameBoard.getMessage("gameWonMsg"));
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
            view.showMessage(currentPlayer.getName() + gameBoard.getMessage("leaveJailMsg"));
        }
        if (!currentPlayer.isJailed()) {
            boolean hasPassedStart = gameBoard.rollDieMovePlayer();
            view.showMessage(currentPlayer.getName() + " " + gameBoard.getMessage("rollDiceMsg"));
            view.update(gameBoard.getPlayers(), gameBoard.getFields(), gameBoard.getDiceCup());
            gameBoard.fieldAction(currentPlayer);
            GameBoard.removeBankruptPlayers(gameBoard.getPlayers());
            view.update(gameBoard.getPlayers(), gameBoard.getFields(), gameBoard.getDiceCup());

            if(gameBoard.isGameover())
                return;
            if (hasPassedStart) {
                view.showMessage(gameBoard.getMessage("passedStartMsg"));
            }
            // Checks if player gets an extra turn
            // TODO Should you get extra turn if you land on goToJail?

            if (gameBoard.getDiceCup().diceAreEqual()) {
                view.showMessage(currentPlayer.getName() + gameBoard.getMessage("extraTurn"));
                playTurn(currentPlayer);
            }
        }
    }
}



