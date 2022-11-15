package Controllers;

import Language.LanguageHandler;
import gui_fields.*;
import gui_main.GUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import Enities.*;
import ChanceCards.*;


public class GameHandler {
    final int playerCount = 2;
    Player[] players = new Player[playerCount];

    boolean[] jailedPlayers = new boolean[playerCount];
    GUI_Controller gui_controller;
    int[] playerPos = new int[playerCount];
    int playerTurn;
    Chance[] cards;
//    final int[] fieldValues = {1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 5, 5};

    GameBoard gameBoard = new GameBoard();

    DiceCup diceCup = new DiceCup();

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

    public void playGame() {
        playerTurn = 0;

        for (Player player : players) {
            movePlayer(0, player);
        }
        while (true) {
            Player currentPlayer = players[playerTurn];
            playRound(currentPlayer);
            if (isGameover()) {
                //needs to be changed to count money from other players.
                // Should be a scoreboard showing first to last according to amount of money
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
        Chance[] temp = new Chance[cards.size()];
        return cards.toArray(new Chance[0]);
    }

    private void checkFieldType(Player currentPlayer) {
        GUI_Field field = currentPlayer.getCar().getPosition();
        if (field instanceof GUI_Street) {
            onStreet((GUI_Street) field, currentPlayer);

        } else if (field instanceof GUI_Chance) onChance(cards[0], currentPlayer);
        else if (field instanceof GUI_Jail) return;
        else if (field instanceof GUI_Start) return;
        else if (field instanceof GUI_Refuge) return;
        else return;
    }

    public void onStreet(GUI_Street street, Player currentPlayer) {
        //maybe prompt that you landed
        int rent = Integer.parseInt(street.getRent());
        if (street.getOwnerName().equals("Bank")) {
            //currently just shows player id, would be nice to have full names for GUI clicks
            street.setOwnerName(String.valueOf(playerTurn));
            currentPlayer.setBalance(currentPlayer.getBalance() - rent, gui_controller);


            //dont think we need an else, since we still want to purchace and then gameover if the player cant afford
        } else {
            players[Integer.parseInt(street.getOwnerName())].setBalance(players[Integer.parseInt(street.getOwnerName())].getBalance() + rent, gui_controller);
            currentPlayer.setBalance(currentPlayer.getBalance() - rent, gui_controller);
        }
    }

    private void onChance(Chance chance, Player currentPlayer) {
        gui_controller.displayText(currentPlayer.getName() + " " + LanguageHandler.chanceCardMsg() + chance.getDesc());
        if (chance instanceof MoveChance moveChance) moveChance.pullCard(currentPlayer);

    }


    private void playRound(Player currentPlayer) {
        //dicecup with 1 die is wierd, but works
        diceCup.roll();
        int diceSum = diceCup.getSum();

        //might need to change language class to a txt file instead, make sure to check with helpers
        if (currentPlayer.isJailed()) {
            currentPlayer.setBalance(currentPlayer.getBalance() - 1, gui_controller);
            currentPlayer.setJailed(false);
        }
        gui_controller.showMessage(currentPlayer.getName() + " " + LanguageHandler.rollDiceMsg());
        gui_controller.setDie(diceSum);

        //make sure player doesnt move out of bounds
        int nextPos = currentPlayer.getPos() + diceSum;
        if (nextPos >= 24) currentPlayer.setPos(nextPos - 24);
        else currentPlayer.setPos(nextPos);

        //move player
        movePlayer(currentPlayer.getPos(), currentPlayer);

        //onfield handeling, maybe could be put in another class
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

