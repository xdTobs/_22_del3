package Controller;

import Enities.GameBoard;
import Enities.Player;
import Language.LanguageHandler;
import View.View;


/**
 * The type Game handler.
 */
public class GameHandler {
    private View view;

    private GameBoard gameBoard;

    public GameHandler(View view, GameBoard gameBoard) {
        // Remember to give the car a color, so p1 and p2 don't have same colors.
        this.view = view;
        this.gameBoard = gameBoard;
//        GUI_Car car1 = new GUI_Car();
//        car1.setPrimaryColor(Color.black);
//        GUI_Car car2 = new GUI_Car();
//        car2.setPrimaryColor(Color.white);
//        this.gui_controller = new GUI_Controller(new GUI_Player[]{this.players[0].getGuiPlayer(), this.players[1].getGuiPlayer()}, gameBoard.getGuiFields());


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
            if (isGameover()) {
//                 TODO: Should be a scoreboard showing first to last according to amount of money
//                gui_controller.showMessage(currentPlayer.getName() + LanguageHandler.gameWonMsg());
                break;
            }
            nextPlayer();
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

        // We make an action according to what field the player landed on.
        gameBoard.fieldAction(currentPlayer);

    }

    private void resetPlayerPositions() {
        gameBoard.resetPlayerPositions();
        view.update(gameBoard.getDiceCup(), gameBoard.getPlayers(), gameBoard.getFields());
    }


    private void nextPlayer() {
        if (playerTurn >= players.length - 1) {
            playerTurn = 0;
        } else {
            playerTurn++;
        }
    }

    // The only player that can lose money is the current player, I think. Maybe the chance cards can take money from the player, but I don't think so.
    // Never mind. There is a chance card that takes money from all other players and gives to current player. We need to check all players.
    private boolean isGameover() {
        for (Player player : players) {
            if (player.getBalance() <= 0) {
                return true;
            }
        }
        return false;
    }
}

