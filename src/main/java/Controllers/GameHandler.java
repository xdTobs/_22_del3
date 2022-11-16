package Controllers;

import Language.LanguageHandler;
import gui_fields.*;

import java.awt.*;

import Enities.*;
import ChanceCards.*;


/**
 * The type Game handler.
 */
public class GameHandler {
    /**
     * Number of players
     */
    final int playerCount = 2;
    /**
     * The Players.
     */
    public Player[] players = new Player[playerCount];
    /**
     * The Gui controller, where we manipulate the graphics of the game.
     */
    GUI_Controller gui_controller;
    /**
     * Keeps track of current player index. 0 = player 1, 1 = player 2, etc. We can index into players array with this.
     */
    int playerTurn;

    /**
     * The Game board. Most of the logic of gets handled in gameHandler, but lower level logic is handled in gameBoard.
     */
    public GameBoard gameBoard;
    /**
     * The Dice cup. We roll our die in this.
     */
    DiceCup diceCup = new DiceCup();

    /**
     * Instantiates a new Game handler, the motor running the whole thing.
     */
    public GameHandler() {
        // Remember to give the car a color, so p1 and p2 don't have same colors.
        GUI_Car car1 = new GUI_Car();
        car1.setPrimaryColor(Color.black);
        GUI_Car car2 = new GUI_Car();
        car2.setPrimaryColor(Color.white);


        this.players[0] = new Player(LanguageHandler.getPlayerName1(), 20, car1);
        this.players[1] = new Player(LanguageHandler.getPlayerName2(), 20, car2);

        Deck cards = new Deck();
        gameBoard = new GameBoard(cards);
        this.gui_controller = new GUI_Controller(new GUI_Player[]{this.players[0].getGuiPlayer(), this.players[1].getGuiPlayer()}, gameBoard.getGuiFields());


    }

    /**
     * Play game.
     */
    public void playGame() {
        playerTurn = 0;

        // Moves all player to the start position.
        for (Player player : players) {
            movePlayer(0, player);
        }
        while (true) {
            Player currentPlayer = players[playerTurn];
            playTurn(currentPlayer);
            if (isGameover()) {
                // TODO: Should be a scoreboard showing first to last according to amount of money
                gui_controller.showMessage(currentPlayer.getName() + LanguageHandler.gameWonMsg());
                break;
            }
            nextPlayer();
        }
    }


    /**
     * A player makes his turn.
     *
     * @param currentPlayer the current player
     */
    private void playTurn(Player currentPlayer) {
        // We roll our dice.
        diceCup.roll();
        int diceSum = diceCup.getSum();

        // If a player was jailed last turn he needs to pay a fine to get out.
        if (currentPlayer.isJailed()) {
            currentPlayer.addBalance(-1);
            currentPlayer.setJailed(false);
        }

        gui_controller.showMessage(currentPlayer.getName() + " " + LanguageHandler.rollDiceMsg());
        gui_controller.setDie(diceSum);

        // Make sure player doesnt move out of bounds. If the check is true that means the player has moved a whole lap, and should be awarded start money, 2$.
        int newPosition = currentPlayer.getPosition() + diceSum;
        if (newPosition >= 24) {
            newPosition = newPosition - 24;
            gui_controller.showMessage(LanguageHandler.passedStartMsg());
            currentPlayer.addBalance(2);
        }

        // Move player
        movePlayer(newPosition, currentPlayer);
        // We make an action according to what field the player landed on.
        gameBoard.fieldAction(currentPlayer, gui_controller, players);
    }

    private void movePlayer(int pos, Player currentPlayer) {
        currentPlayer.setPosition(pos);
        gui_controller.movePlayer(pos, currentPlayer);
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

