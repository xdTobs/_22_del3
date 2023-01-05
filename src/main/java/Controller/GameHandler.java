package Controller;

import Enities.ActualChanceCard;
import Enities.ActualFields;
import Enities.GameBoard;
import Enities.Player;
import Language.LanguageHandler;
import View.*;

import java.io.IOException;


/**
 * The controller.
 */
public class GameHandler {
    final private View view;
    final private GameBoard gameBoard;
    private LanguageHandler language;
    private ActualChanceCard acc;

    {
        try {
            language = new LanguageHandler();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Instantiates a new Game controller.
     *
     * @param view      the view
     * @param gameBoard the gameBoard, our model
     */
    public GameHandler(View view, GameBoard gameBoard) {
        this.view = view;
        this.gameBoard = gameBoard;
        int playerCount = view.promptPlayerCount(2, 4);
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
                view.showMessage(gameBoard.findLoser() + language.languageMap.get("gameLostMsg"));
                view.showMessage(gameBoard.findWinner() + language.languageMap.get("gameWonMsg"));
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
        view.update(gameBoard.getDiceCup(), gameBoard.getPlayers(), gameBoard.getFields());
    }


    private void playTurn(Player currentPlayer) {
        // If a player was jailed last turn he needs to pay a fine to get out or use a get out of jail free card.

        if (currentPlayer.isJailed()) {
            // gameBoard.payFine(currentPlayer);
            currentPlayer.addToJailedCounter();
            int jailedCounter = currentPlayer.getJailedCounter();
            if (jailedCounter == 2) {
                currentPlayer.setJailed(false);
                currentPlayer.setJailedCounter(0);
            }
            view.showMessage(currentPlayer.getName() + language.languageMap.get("leaveJailMsg"));
            return;
        }
        boolean hasPassedStart = gameBoard.rollDieMovePlayer();
        view.showMessage(currentPlayer.getName() + " " + language.languageMap.get("rollDiceMsg"));
        view.updatePlayerLocations(gameBoard.getPlayers());
        view.updateDie(gameBoard.getDiceCup());
        gameBoard.fieldAction(currentPlayer);
        view.update(gameBoard.getDiceCup(), gameBoard.getPlayers(), gameBoard.getFields());

        if (hasPassedStart) {
            view.showMessage(language.languageMap.get("passedStartMsg"));
        }
        // Checks if player gets an extra turn
        int[] extraTurn = gameBoard.getDiceCup().getArray();
        if (extraTurn[0] == extraTurn[1]) {
            view.showMessage(currentPlayer.getName() + language.languageMap.get("extraTurn"));
            playTurn(currentPlayer);
        }


    }
}



