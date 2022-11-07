import gui_fields.*;
import gui_main.GUI;

import java.awt.*;

public class DiceGame {
    GUI_Player[] players = new GUI_Player[2];
    GUI gui;
    GUI_Field[] fields;
    GUI_Street[] streets;
    Language language;
    int[] playerPos = new int[2];
    int playerTurn;
    final int[] fieldValues = {1,1,1,1,2,2,2,2,3,3,3,3,4,4,5,5};


    DiceCup diceCup = new DiceCup();

    public DiceGame() {
        this.language = new Language();
        // Remember to give the car a color, so p1 and p2 don't have same colors.
        GUI_Car car1 = new GUI_Car();
        car1.setPrimaryColor(Color.black);
        GUI_Car car2 = new GUI_Car();
        car2.setPrimaryColor(Color.white);
        this.players[0] = new GUI_Player(language.playerName1, 1000,car1);
        this.players[1] = new GUI_Player(language.playerName2, 1000,car2);
        this.streets = initializeStreets();
        this.fields = initializeFields(streets);
        this.gui = new GUI(fields, Color.white);

        for (GUI_Player player :
                players) {
            gui.addPlayer(player);
        }
    }

    private void playGame() {
        playerTurn = 0;

        for (GUI_Player player : players){
            movePlayer(0,player);
        }
        while (true) {
            GUI_Player currentPlayer = players[playerTurn];
            playRound(currentPlayer);
            if (isGameover()) {
                this.gui.showMessage(currentPlayer.getName() + language.gameWon);
                break;
            }
            nextPlayer();
        }
    }

    private GUI_Field[] initializeFields(GUI_Street[] streets) {
        GUI_Field[] fields;
        fields = new GUI_Field[24];
        //street increment
        int j = 0;
        for (int i = 0; i < fields.length; i++) {

                switch (i) {
                    case (0):
                        fields[i] = new GUI_Start("Start", " ", " ", Color.white, Color.black);
                        break;
                    case(3):
                    case(9):
                    case(15):
                    case(21):
                        //implement chance cards here
                        fields[i] = new GUI_Street(" ", " ", " ", " ", Color.BLACK, Color.BLACK);
                        break;
                    case(6):
                        //implement proper constructor
                        fields[i] = new GUI_Jail();
                        break;
                    case(12):
                        //implement proper constructor
                        fields[i] = new GUI_Refuge();
                        break;
                    case(18):
                        //implement go to jail
                        fields[i] = new GUI_Street(" ", " ", " ", " ", Color.BLACK, Color.BLACK);
                        break;
                    default:
                        fields[i] = streets[j];
                        j++;
                        break;
                }




        }




        return fields;
    }
    private GUI_Street[] initializeStreets() {
        GUI_Street[] streets;
        streets = new GUI_Street[16];

        for (int i = 0; i < streets.length; i++) {
            streets[i] = new GUI_Street(language.fieldNames[i], Integer.toString(fieldValues[i]), " ", Integer.toString(fieldValues[i]), Color.white, Color.BLACK);
            streets[i].setOwnerName("Bank");
        }
        return streets;
    }


    private void playRound(GUI_Player currentPlayer) {

        diceCup.roll();
        int diceSum = diceCup.getSum();
        gui.showMessage(currentPlayer.getName() + " " + language.onRollDice);

        gui.setDie(diceSum);
        playerPos[playerTurn]+=diceSum;
        if (playerPos[playerTurn]>=24)playerPos[playerTurn]-=24;
        movePlayer(playerPos[playerTurn], currentPlayer);
        // Check if player is going to be under 0 in value.
        // If so, value is set to value, instead of negative value.
        int fieldValue = fieldValues[diceSum];
        int player_balance = currentPlayer.getBalance();
        int new_balance = player_balance + fieldValue;
        currentPlayer.setBalance(Math.max(new_balance, 0));
    }

    private void movePlayer(int pos, GUI_Player currentPlayer) {

currentPlayer.getCar().setPosition(fields[pos]);

    }




    private void nextPlayer() {
        if (playerTurn >= players.length - 1) {
            playerTurn = 0;
        } else {
            playerTurn++;
        }
    }

    private boolean isGameover() {
        int winLimit = 3000;
        return players[0].getBalance() >= winLimit || players[1].getBalance() >= winLimit;
    }

    public static void main(String[] args) {
        DiceGame game = new DiceGame();
        game.playGame();
    }
}

