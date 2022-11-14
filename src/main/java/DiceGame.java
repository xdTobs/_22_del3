import gui_fields.*;
import gui_main.GUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DiceGame {
    final int playerCount = 2;
    Player[] players = new Player[playerCount];
    GUI gui;
    GUI_Field[] fields;

    GUI_Street[] streets;
    boolean[] jailedPlayers =new boolean[playerCount];
    Language language;
    GUI_Controller gui_controller;
    int[] playerPos = new int[playerCount];
    int playerTurn;
    Chance[] cards;
    final int[] fieldValues = {1,1,1,1,2,2,2,2,3,3,3,3,4,4,5,5};
    final Color[] streetColors = {Color.GREEN};


    DiceCup diceCup = new DiceCup();

    public DiceGame() {
        this.language = new Language();

        // Remember to give the car a color, so p1 and p2 don't have same colors.
        GUI_Car car1 = new GUI_Car();
        car1.setPrimaryColor(Color.black);
        GUI_Car car2 = new GUI_Car();
        car2.setPrimaryColor(Color.white);
        this.players[0] = new Player(language.playerName1, 20,car1);
        this.players[1] = new Player(language.playerName2, 20,car2);
        this.streets = initializeStreets();
        this.fields = initializeFields(streets);

        this.gui = new GUI(fields, Color.white);
        this.gui_controller = new GUI_Controller(gui,players,fields);
        gui_controller.addPlayersToGUI();
        this.cards = initializeChanceCards();
    }

    private void playGame() {
        playerTurn = 0;

        for (Player player : players){
            movePlayer(0,player);
        }
        while (true) {
            Player currentPlayer = players[playerTurn];
            playRound(currentPlayer);
            if (isGameover()) {
                //needs to be changed to count money from other players.
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
//GUI_Street(" ", " ", " ", " ", Color.BLACK, Color.BLACK);
                switch (i) {
                    case (0):
                        fields[i] = new GUI_Start("Start", " ", " ", Color.white, Color.black);
                        break;
                    case(3):
                    case(9):
                    case(15):
                    case(21):

                        fields[i] = new GUI_Chance();
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
                        fields[i] = new GUI_Refuge();
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
            Color c;
            if (i<8){
                c = Color.green;
            }
            else {
                c = Color.blue;
            }
            streets[i] = new GUI_Street(language.fieldNames[i], Integer.toString(fieldValues[i]), " ", Integer.toString(fieldValues[i]), c, Color.BLACK);
            streets[i].setOwnerName("Bank");
        }
        return streets;
    }
    private Chance[] initializeChanceCards(){
        List<Chance> cards = new ArrayList<>();
        cards.add(new moveChance(gui_controller,2));
        Chance[] temp = new Chance[cards.size()];
return cards.toArray(new Chance[0]);
    }
    private void checkFieldType(Player currentPlayer){
        GUI_Field field = currentPlayer.getCar().getPosition();
        if (field instanceof GUI_Street street){
            onStreet(street,currentPlayer);

        }
        else if (field instanceof GUI_Chance) onChance(cards[0],currentPlayer);
        else if(field instanceof GUI_Jail) return ;
        else if (field instanceof GUI_Start) return ;
        else if (field instanceof GUI_Refuge) return ;
        else return ;
    }
    public void onStreet (GUI_Street street,Player currentPlayer){
        //maybe prompt that you landed
        int rent =Integer.parseInt(street.getRent());
        if (street.getOwnerName().equals("Bank")){
                //currently just shows player id, would be nice to have full names for GUI clicks
                street.setOwnerName(String.valueOf(playerTurn));
                currentPlayer.setBalance(currentPlayer.getBalance()-rent,gui_controller);


            //dont think we need an else, since we still want to purchace and then gameover if the player cant afford
        }
        else{
            players[Integer.parseInt(street.getOwnerName())].setBalance(players[Integer.parseInt(street.getOwnerName())].getBalance()+rent,gui_controller);
            currentPlayer.setBalance(currentPlayer.getBalance()-rent,gui_controller);
        }
    }
    private void onChance(Chance chance,Player currentPlayer){
        gui_controller.displayText(currentPlayer.getName()+" "+language.onChanceCard+chance.getDesc());
        if (chance instanceof moveChance moveChance) moveChance.pullCard(currentPlayer);

    }


    private void playRound(Player currentPlayer) {
//dicecup with 1 die is wierd, but works
        diceCup.roll();
        int diceSum = diceCup.getSum();
        //might need to change language class to a txt file instead, make sure to check with helpers
        if (currentPlayer.isJailed()){
            currentPlayer.setBalance(currentPlayer.getBalance()-1,gui_controller);
            currentPlayer.setJailed(false);
        }
        gui.showMessage(currentPlayer.getName() + " " + language.onRollDice);

        gui.setDie(diceSum);
        //make sure player doesnt move out of bounds
        int nextPos =currentPlayer.getPos()+diceSum;
        if (nextPos>=24)currentPlayer.setPos(nextPos-24);
        else currentPlayer.setPos(nextPos);

        //move player
        movePlayer(currentPlayer.getPos(),currentPlayer);

        //onfield handeling, maybe could be put in another class
        checkFieldType(currentPlayer);


    }

    private void movePlayer(int pos, Player currentPlayer) {
        gui_controller.movePlayer(pos, currentPlayer.getId());
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
        int winLimit = 0;
        boolean res = false;
        for (Player player : players){
            if (player.getBalance()<0) res = true;
        }
        return res;
    }

    public GUI_Field[] getFields() {
        return fields;
    }

    public static void main(String[] args) {
        DiceGame game = new DiceGame();
        game.playGame();

    }
}

