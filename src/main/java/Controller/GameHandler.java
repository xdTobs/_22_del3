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
    private View.GUI_View view;

    private GameBoard gameBoard;

    public GameHandler(GUI_View view, GameBoard gameBoard) {
        // Remember to give the car a color, so p1 and p2 don't have same colors.
        this.view = view;
        this.gameBoard = gameBoard;
    }

    /**
     * Play game.
     */
    public void playGame() {
        int playerCount  = view.promptPlayerCount();

        Player[] players = new Player[playerCount];
        for (int j = 0; j < playerCount; j++) {

            players[j] = new Player("Player"+ Math.addExact(j,1),20);
        }
        gameBoard.setPlayers(players);
        view.addPlayersToGui(gameBoard.getPlayers());
        // Moves all player to the start position.
        resetPlayerPositions();
        while (true) {
            Player currentPlayer = gameBoard.getCurrentPlayer();
            playTurn(currentPlayer);
            if (gameBoard.isGameover()) {
                // TODO We need to add correct winner message. A list from loser to winner. The one with most money is winner.
                view.showMessage(currentPlayer.getName() + " has lost!!!");
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

        // We roll our dice.
        boolean hasPassedStart = gameBoard.rollDieMovePlayer();
        view.showMessage(currentPlayer.getName() + " " + LanguageHandler.rollDiceMsg());
        if (hasPassedStart) {
            view.showMessage(LanguageHandler.passedStartMsg());
        }
        view.updatePlayerLocations(gameBoard.getPlayers());
        view.updateDie(gameBoard.getDiceCup());

        if (gameBoard.currentPlayerIsOnChanceField()) {
            gameBoard.pullNewChanceCard();
            ChanceCard chanceCard = gameBoard.getLatestChanceCard();
            if (chanceCard instanceof PickStreetChanceCard pickStreetChanceCard) {
                pickStreetChanceCard.promptPlayerForStreet(view);
            }
        }
        gameBoard.fieldAction(currentPlayer);
        view.update(gameBoard.getDiceCup(), gameBoard.getPlayers());
    }


    private void resetPlayerPositions() {
        gameBoard.resetPlayerPositions();
        view.update(gameBoard.getDiceCup(), gameBoard.getPlayers());
    }
}

