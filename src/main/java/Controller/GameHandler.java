package Controller;

import Enities.ChanceCards.ChanceCard;
import Enities.ChanceCards.PickStreetChanceCard;
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
    
    private LanguageHandler language;

    {
        try {
            language = new LanguageHandler();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final private GameBoard gameBoard;

    /**
     * Instantiates a new Game controller.
     *
     * @param view      the view
     * @param gameBoard the gameBoard, our model
     */
    public GameHandler(View view, GameBoard gameBoard) {
        this.view = view;
        this.gameBoard = gameBoard;
        int playerCount = view.promptPlayerCount();
        gameBoard.createPlayers(playerCount);
        view.addPlayersToGui(gameBoard.getPlayers());
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

    private void playTurn(Player currentPlayer) {
        // If a player was jailed last turn he needs to pay a fine to get out or use a get out of jail free card.
        if (currentPlayer.isJailed()) {
            gameBoard.payFine(currentPlayer);
            view.showMessage(currentPlayer.getName() + language.languageMap.get("leaveJailMsg"));
        }

        boolean hasPassedStart = gameBoard.rollDieMovePlayer();
        view.showMessage(currentPlayer.getName() + " " + language.languageMap.get("rollDiceMsg"));
        if (hasPassedStart) {
            view.showMessage(language.languageMap.get("passedStartMsg"));
        }
        view.updatePlayerLocations(gameBoard.getPlayers());
        view.updateDie(gameBoard.getDiceCup());

        // This is the chance card where the player gets to pick a street to go to and then gets it for free or has to pay rent.
        // We need a special case for it, because we need to let the player choose the street first and then run the card.
        // All other cards need no special case, because they are executed directly.
        if (gameBoard.currentPlayerIsOnChanceField()) {
            gameBoard.getDeck().pullCard();
            ChanceCard card = gameBoard.getDeck().getLatestChanceCard();
            if (card instanceof PickStreetChanceCard pickStreetChanceCard) {
                getPlayerChoicePickStreetChanceCard(pickStreetChanceCard);
            }
        }
        gameBoard.fieldAction(currentPlayer);
        view.update(gameBoard.getDiceCup(), gameBoard.getPlayers(), gameBoard.getFields());
    }

    private void getPlayerChoicePickStreetChanceCard(PickStreetChanceCard pickStreetChanceCard) {
        String message = language.languageMap.get("chanceCardMsg")+ " " + language.languageMap.get("onPickFieldChance");
        String[] choices = pickStreetChanceCard.getStreetChoiceNames();
        String answer = view.promptPlayer(choices, message);
        pickStreetChanceCard.setPickedStreet(answer, gameBoard);
    }


    private void resetPlayerPositions() {
        gameBoard.resetPlayerPositions();
        view.update(gameBoard.getDiceCup(), gameBoard.getPlayers(), gameBoard.getFields());
    }
}

