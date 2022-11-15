package Controllers;

import Enities.Fields.Street;
import Language.LanguageHandler;
import gui_fields.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
    Player[] players = new Player[playerCount];
    /**
     * The Gui controller, where we manipulate the graphics of the game.
     */
    GUI_Controller gui_controller;
    /**
     * Keeps track of current player index. 0 = player 1, 1 = player 2, etc. We can index into players array with this.
     */
    int playerTurn;
    /**
     * The Cards, chance.
     */
    Chance[] cards;
    /**
     * The Game board. Most of the logic of gets handled in gameHandler, but lower level logic is handled in gameBoard.
     */
    GameBoard gameBoard = new GameBoard();
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

//        this.gui = new GUI(gameBoard.getGuiFields(), Color.white);

        this.gui_controller = new GUI_Controller(new GUI_Player[]{this.players[0].getGuiPlayer(), this.players[1].getGuiPlayer()}, gameBoard.getGuiFields());
        this.cards = initializeChanceCards();
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


    private Chance[] initializeChanceCards() {
        List<Chance> cards = new ArrayList<>();
        /*cards.add(new moveChance(
                // TODO implement i
                LanguageHandler.moveToMsg(3) + " " + gameBoard.getFields()[3].getTitle(),
                gui_controller,
                gameBoard.getFields()[2])
        );*/
        cards.add(new MoveChance(gui_controller, 2));
        return cards.toArray(new Chance[0]);
    }


    // When the player lands on a field, we find out which one here, and then we take action according to field type.
    private void checkFieldType(Player currentPlayer) {
        var playerPosition = currentPlayer.getPos();
        var field = gameBoard.getField(playerPosition);
        GUI_Field guiField = field.getGuiField();
        if (field instanceof Street) {
            GUI_Street guiStreet = (GUI_Street) field.getGuiField();
            onStreet(guiStreet, currentPlayer);
        } else if (guiField instanceof GUI_Chance) {
            onChance(cards[0], currentPlayer);
        } else if (guiField instanceof GUI_Jail) {
            onJail(currentPlayer);
        } else if (guiField instanceof GUI_Start) {
            onStart();
        } else if (guiField instanceof GUI_Refuge) {
            onRefuge();
        }
    }

    private void onStart() {
        gui_controller.showMessage(LanguageHandler.passedStartMsg());
    }

    private void onRefuge() {
        gui_controller.showMessage(LanguageHandler.parkingMsg());
    }

    /**
     * We have landed on street.
     *
     * @param street        the street
     * @param currentPlayer the current player
     */
    public void onStreet(GUI_Street street, Player currentPlayer) {
        //maybe prompt that you landed
        int rent = Integer.parseInt(street.getRent());
        if (street.getOwnerName().equals("Bank")) {
            //currently just shows player id, would be nice to have full names for GUI clicks
            street.setOwnerName(currentPlayer.getName());
            currentPlayer.setBalance(currentPlayer.getBalance() - rent);


            //dont think we need an else, since we still want to purchace and then gameover if the player cant afford
        } else {
            // TODO Test if this works. I think it should.
            String houseOwnerName = street.getOwnerName();
            Player houseOwner = null;
            for (Player player : players) {
                if (player.getName().equals(houseOwnerName)) {
                    houseOwner = player;
                }
            }
            assert houseOwner != null;
            // If you land on your own house, you don't have to pay rent. But we can ignore handling that, because paying yourself $2 dollars makes no difference. The gameover check comes much later.
            houseOwner.setBalance(houseOwner.getBalance() + rent);
            currentPlayer.setBalance(currentPlayer.getBalance() - rent);
        }
    }

    /**
     * We have landed on chance, we get a card.
     *
     * @param chance        the chance card
     * @param currentPlayer the current player
     */
    private void onChance(Chance chance, Player currentPlayer) {
        gui_controller.displayText(currentPlayer.getName() + " " + LanguageHandler.chanceCardMsg() + chance.getDesc());
        if (chance instanceof MoveChance moveChance) moveChance.pullCard(currentPlayer);

    }

    /**
     * We have landed on jail, we need to pay money next turn, unless we have special card.
     *
     * @param currentPlayer the current player
     */
    private void onJail(Player currentPlayer) {
        currentPlayer.setJailed(true);
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
            currentPlayer.setBalance(currentPlayer.getBalance() - 1);
            currentPlayer.setJailed(false);
        }

        gui_controller.showMessage(currentPlayer.getName() + " " + LanguageHandler.rollDiceMsg());
        gui_controller.setDie(diceSum);

        // Make sure player doesnt move out of bounds. If the check is true that means the player has moved a whole lap, and should be awarded start money, 2$.
        int newPosition = currentPlayer.getPos() + diceSum;
        if (newPosition >= 24) {
            newPosition = newPosition - 24;
            gui_controller.showMessage(LanguageHandler.passedStartMsg());
            currentPlayer.setBalance(currentPlayer.getBalance() + 2);
        }

        //move player
        movePlayer(newPosition, currentPlayer);
        checkFieldType(currentPlayer);
    }

    private void movePlayer(int pos, Player currentPlayer) {
        currentPlayer.setPos(pos);
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

