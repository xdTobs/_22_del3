package Controller;

import ChanceCards.ChanceCard;
import ChanceCards.PickStreetChanceCard;
import Enities.GameBoard;
import Enities.Player;
import Language.LanguageHandler;
import View.GUI_View;


/**
 * The type Game handler.
 */
public class GameHandler {
    final private View.GUI_View view;

    final private GameBoard gameBoard;

    public GameHandler(GUI_View view, GameBoard gameBoard) {
        // Remember to give the car a color, so p1 and p2 don't have same colors.
        this.view = view;
        this.gameBoard = gameBoard;
        int playerCount = view.promptPlayerCount();
        gameBoard.createPlayers(playerCount);
        view.addPlayersToGui(gameBoard.getPlayers());
    }

    /**
     * Play game.
     */
    public void playGame() {

        // Moves all player to the start position.
        resetPlayerPositions();
        while (true) {
            Player currentPlayer = gameBoard.getCurrentPlayer();
            playTurn(currentPlayer);
            if (gameBoard.isGameover()) {

                view.showMessage(gameBoard.findLoser() + LanguageHandler.gameLostMsg());
                view.showMessage(gameBoard.findWinner() + LanguageHandler.gameWonMsg());
                break;
            }
            gameBoard.nextPlayer();
        }
    }

    public void playTurn(Player currentPlayer) {
        // If a player was jailed last turn he needs to pay a fine to get out or use a get out of jail free card.
        if (currentPlayer.isJailed()) {
            gameBoard.payFine(currentPlayer);
            view.showMessage(currentPlayer.getName() + LanguageHandler.jailMsg(currentPlayer.getName()));
        }

        boolean hasPassedStart = gameBoard.rollDieMovePlayer();
        view.showMessage(currentPlayer.getName() + " " + LanguageHandler.rollDiceMsg());
        if (hasPassedStart) {
            view.showMessage(LanguageHandler.passedStartMsg());
        }
        view.updatePlayerLocations(gameBoard.getPlayers());
        view.updateDie(gameBoard.getDiceCup());

        // This is the chance card where the player gets to pick a street to go to and then gets it for free or has to pay rent.
        // We need a special case for it, because we need to let the player choose the street first and then run the card.
        // All other cards need no special case, because they are executed directly.
        if (gameBoard.currentPlayerIsOnChanceField()) {
            gameBoard.pullNewChanceCard();
            ChanceCard chanceCard = gameBoard.getLatestChanceCard();
            if (chanceCard instanceof PickStreetChanceCard pickStreetChanceCard) {
                playerPicksStreetChanceCard(pickStreetChanceCard);
                view.updatePlayerLocations(gameBoard.getPlayers());
                view.updatePlayerBalances(gameBoard.getPlayers());
            }
        }
        gameBoard.fieldAction(currentPlayer);
        view.update(gameBoard.getDiceCup(), gameBoard.getPlayers());
    }

    private void playerPicksStreetChanceCard(PickStreetChanceCard pickStreetChanceCard) {
        String message = LanguageHandler.chanceCardMsg() + " " + LanguageHandler.onPickFieldChance();
        String[] choices = pickStreetChanceCard.getStreetChoiceNames();
        String answer = view.promptPlayer(choices, message);
        pickStreetChanceCard.goToSelectedStreet(answer, gameBoard);
    }


    private void resetPlayerPositions() {
        gameBoard.resetPlayerPositions();
        view.update(gameBoard.getDiceCup(), gameBoard.getPlayers());
    }
}

