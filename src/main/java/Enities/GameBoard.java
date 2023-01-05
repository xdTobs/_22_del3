package Enities;

import Enities.ChanceCards.Deck;
import Enities.ChanceCards.GetOutOfJailChanceCard;
import Enities.Fields.*;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class for the game board.
 * Contains all the fields and the chance deck and diceCup.
 * This is the model.
 */
public class GameBoard {

    private final DiceCup diceCup = new DiceCup();
    private final HashMap<Color,int[]> pairs = new HashMap<>();
    private final HashMap<Player, List<RentableField>> ownershipMap = new HashMap<>();
    private final Field[] fields = new Field[40];
    private final Deck deck;
    private Player[] players;
    private int playerTurn;

    public ActualChanceCard getAcc() {
        return acc;
    }

    private ActualChanceCard acc;
    private ActualFields actualFields;

    public ActualFields getActualFields() {
        return actualFields;
    }

    public void setActualFields(ActualFields actualFields) {
        this.actualFields = actualFields;
    }

    public void setAcc(ActualChanceCard acc) {
        this.acc = acc;
    }

    /**
     * Instantiates a new Game board.
     */
    public GameBoard() {
        List<Field> temp = new ArrayList<>();
        List<String> content;
        try {
            content = Files.readAllLines(Path.of("docs/fields.csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        for (String s : content) {
            String[] key = s.split(",");
            switch (key[2].trim()) {
                case ("street") -> temp.add(new Street(s));
                case ("tax") -> temp.add(new Tax(s));
                case ("jail") -> temp.add(new Jail(s));
                case ("gotoJail") -> temp.add(new GoToJail(s));
                case ("chance") -> temp.add(new ChanceField(s));
                case ("refugee") -> temp.add(new Parking(s));
                case ("start") -> temp.add(new Start(s));
                case ("brewery") -> temp.add(new Brewery(s));
                case ("ferry") -> temp.add(new Ferry(s));
            }
        }
        temp.toArray(fields);
        List<int[]>streetPairs = new ArrayList<>();
        streetPairs.add(new int[]{1,3});
        streetPairs.add(new int[]{6,8,9});
        streetPairs.add(new int[]{11,13,14});
        streetPairs.add(new int[]{16,18,19});
        streetPairs.add(new int[]{21,23,24});
        streetPairs.add(new int[]{26,27,29});
        streetPairs.add(new int[]{31,32,34});
        streetPairs.add(new int[]{37,39});
        streetPairs.add(new int[]{2,7,17,22,33,36});
        streetPairs.add(new int[]{4,38});
        streetPairs.add(new int[]{5,15,25,35});
        streetPairs.add(new int[]{20});
        streetPairs.add(new int[]{10});
        streetPairs.add(new int[]{30});
        streetPairs.add(new int[]{28,12});



        for (int i = 0; i < streetPairs.size(); i++) {
            for(int j : streetPairs.get(i)){
                Field field = fields[j];
                field.setPairIndexes(streetPairs.get(i));

            }
        }
        this.deck = new Deck();
    }

    /**
     * Gets field.
     *
     * @param position the position
     * @return the field
     */
    public Field getField(int position) {
        return fields[position];
    }


    /**
     * Makes whatever action the field supports.
     *
     * @param currentPlayer the current player
     */
    public void fieldAction(Player currentPlayer) {
        int playerPosition = currentPlayer.getPosition();
        Field field = getField(playerPosition);
        Field boughtField = field.executeFieldAction(this.getActualFields());
        if(boughtField instanceof RentableField rentableBoughtField){
            ownershipMap.get(currentPlayer).add(rentableBoughtField);
        }
    }

    public Field[] getFields() {
        return fields;
    }

    public void resetPlayerPositions() {
        for (Player player : players) {
            player.setPosition(0);
        }
    }

    public Player getCurrentPlayer() {
        return players[playerTurn];
    }

    public DiceCup getDiceCup() {
        return diceCup;
    }

    public boolean currentPlayerIsOnChanceField() {
        return getFieldOfCurrentPlayer() instanceof ChanceField;
    }

    private Field getFieldOfCurrentPlayer() {
        Player currentPlayer = getCurrentPlayer();
        int position = currentPlayer.getPosition();
        return getField(position);
    }

    /**
     * Roll die move player boolean.
     *
     * @return is true if the player has passed start.
     */
    public boolean rollDieMovePlayer() {
        diceCup.roll();
        Player currentPlayer = getCurrentPlayer();
        int playerPosition = currentPlayer.getPosition();
        int newPosition = playerPosition + diceCup.getSum();
        boolean hasPassedStart = false;
        if (newPosition > 39) {
            newPosition = newPosition - 40;
            currentPlayer.addBalance(4000);
            hasPassedStart = true;
        }
        currentPlayer.setPosition(newPosition);
        return hasPassedStart;
    }

    /**
     * Pay fine when you are in jail.
     *
     * @param currentPlayer the current player
     */
    public void payFine(Player currentPlayer) {
        if (currentPlayer.getGetOutOfJailCards() > 0) {
            currentPlayer.removeGetOutOfJailCard();
        } else {
            currentPlayer.addBalance(-1);
        }
        currentPlayer.setJailed(false);
    }

    /**
     * Get players player [ ].
     *
     * @return the player [ ]
     */
    public Player[] getPlayers() {
        return players;
    }

    private void setPlayers(Player[] players) {
        this.players = players;
    }

    /**
     * Next player.
     */
    public void nextPlayer() {
        if (playerTurn >= players.length - 1) {
            playerTurn = 0;
        } else {
            playerTurn++;
        }
    }

    /**
     * Is gameover boolean.
     *
     * @return the boolean
     */

    public boolean isGameover() {
        int alivePlayers = players.length;
        for (int i = 0; i < players.length; i++) {
            if (players[i].getBalance() < 0) {
                alivePlayers -= 1;
            }
            if (alivePlayers == 1) {
                return true;
            }
        }
        return false;
    }


    public String findWinner() {
        String winner = players[0].getName();
        for (int i = 1; i < players.length; i++) {
            if (players[i].getBalance() > 0) {
                winner = players[i].getName();
            }
        }
        return winner;
    }

    public String findLoser() {
        String loser = players[0].getName();
        int loserBalance = players[0].getBalance();
        for (int i = 1; i < players.length; i++) {
            if (players[i].getBalance() < loserBalance) {
                loser = players[i].getName();
                loserBalance = players[i].getBalance();
            }
        }
        return loser;
    }


    public void createPlayers(int playerCount) {
        Player[] players = new Player[playerCount];
        for (int j = 0; j < playerCount; j++) {

            players[j] = new Player("Player" + Math.addExact(j, 1));
        }
        setPlayers(players);
        for (Player p : players){
            ownershipMap.put(p,new ArrayList<>());
        }
    }

    public Deck getDeck() {
        return deck;
    }
}
