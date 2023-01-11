package entities;

import controller.UserIO;
import entities.chancecards.Deck;
import entities.dicecup.DiceCup;
import entities.fields.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class for the game board.
 * Contains all the fields and the chance deck and diceCup.
 * This is the model.
 */
public class GameBoard {


    private final DiceCup diceCup;
    private final HashMap<Player, List<RentableField>> ownershipMap = new HashMap<>();
    private final Field[] fields;
    private final Deck deck;
    private Player[] players;
    private int playerTurn;
    private final ChanceCardImpl chanceCardImpl;
    private final FieldImpl fieldImpl;

    public GameBoard(Field[] fields, UserIO userIO, int playerCount) {
        this(new DiceCup(), fields, userIO, playerCount);
    }

    public GameBoard(DiceCup diceCup, Field[] fields, UserIO userIO, int playerCount) {
        this(diceCup, fields, Deck.setup(), userIO, playerCount);
    }

    public GameBoard(DiceCup diceCup, Field[] fields, Deck deck, UserIO userIO, int playerCount) {
        this.chanceCardImpl = new ChanceCardImpl(this, userIO);
        this.fieldImpl = new FieldImpl(this, userIO);
        this.deck = deck;
        this.diceCup = diceCup;
        this.fields = fields;
        createPlayers(playerCount);
    }

    public static GameBoard setup(Field[] fields, UserIO userIO, int playerCount) {
        // TODO
        //  we need to figure out some way to make simpler tests.
        //  Can we find a way to make the board only 5 square and then test jail on that board?
        //  Can we do that without changing current code to much?
        // We only make fieldPairs if it is the size of the original matador board.
        // We do this, so we can run tests with other board sizes.
        return new GameBoard(fields, userIO, playerCount);
    }

    private static Stream<String> getLineStream() {
        var inputStream = GameBoard.class.getClassLoader().getResourceAsStream("fields.csv");
        if (inputStream == null) {
            throw new IllegalStateException("InputStream should not be null");
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        // First row is keys. Type, position, rent, which we must skip.
        return bufferedReader.lines().skip(1);
    }

    public static Field[] getDefaultFields() {
        var lines = getLineStream();
        // First row is keys. Type, position, rent etc.
        List<Field> temp = lines.map(Field::parse).toList();
        Field[] fields = new Field[temp.size()];
        temp.toArray(fields);
        return initFieldPairs(fields);
    }

    private static Field[] initFieldPairs(Field[] fields) {
        List<FieldPair> fieldPairs = new ArrayList<>();
        fieldPairs.add(new FieldPair(Color.BLUE, Color.WHITE, new int[]{1, 3}));
        fieldPairs.add(new FieldPair(Color.ORANGE, new int[]{6, 8, 9}));
        fieldPairs.add(new FieldPair(Color.YELLOW, new int[]{11, 13, 14}));
        fieldPairs.add(new FieldPair(Color.GRAY, Color.WHITE, new int[]{16, 18, 19}));
        fieldPairs.add(new FieldPair(Color.RED, Color.WHITE, new int[]{21, 23, 24}));
        fieldPairs.add(new FieldPair(Color.WHITE, new int[]{26, 27, 29}));
        fieldPairs.add(new FieldPair(Color.YELLOW, new int[]{31, 32, 34}));
        fieldPairs.add(new FieldPair(Color.MAGENTA, new int[]{37, 39}));
        fieldPairs.add(new FieldPair(Color.BLACK, Color.GREEN, new int[]{2, 7, 17, 22, 33, 36}));
        fieldPairs.add(new FieldPair(Color.LIGHT_GRAY, new int[]{4, 38}));
        fieldPairs.add(new FieldPair(Color.BLUE, Color.WHITE, new int[]{5, 15, 25, 35}));
        fieldPairs.add(new FieldPair(Color.GRAY, Color.WHITE, new int[]{20}));
        fieldPairs.add(new FieldPair(Color.BLACK, new int[]{10}));
        fieldPairs.add(new FieldPair(Color.WHITE, Color.BLACK, new int[]{30}));
        fieldPairs.add(new FieldPair(Color.RED, Color.WHITE, new int[]{28, 12}));
        fieldPairs.add(new FieldPair(Color.RED, Color.WHITE, new int[]{0}));

        int i = 0;
        for (FieldPair f : fieldPairs) {
            for (int id : f.getFieldIds()) {
                Field field = fields[id];
                field.setPair(fieldPairs.get(i));
            }
            i++;
        }
        return fields;
    }


    public ChanceCardImpl getChanceCardImpl() {
        return chanceCardImpl;
    }

    public HashMap<Player, List<RentableField>> getOwnershipMap() {
        return ownershipMap;
    }

    public FieldImpl getFieldImpl() {
        return fieldImpl;
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
        Field boughtField = field.executeFieldAction(fieldImpl);
        if (boughtField instanceof RentableField rentableBoughtField) {
            ownershipMap.get(currentPlayer).add(rentableBoughtField);
        }
    }

    public int totalPlayerValue(Player p) {
        int totalPlayerValue = 0;
        List<RentableField> playerOwnedFields = ownershipMap.get(p);
        for (RentableField field : playerOwnedFields) {
            totalPlayerValue += field.getPrice();
            if (field instanceof Street street) {
                int housesprice = street.getHousePrice();
                int houses = street.getHouses();
                totalPlayerValue += houses * housesprice;
            }
        }
        totalPlayerValue += p.getBalance();
        return totalPlayerValue;
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
     * Moves the player and returns true if player has passed start.
     *
     * @return is true if the player has passed start.
     */
    public boolean movePlayer() {
        return movePlayer(diceCup.getSum());
    }

    public boolean movePlayer(int diceValue) {
        Player currentPlayer = getCurrentPlayer();
        int playerPosition = currentPlayer.getPosition();
        int newPosition = playerPosition + diceValue;
        return setNewPosition(newPosition);
    }

    public boolean setNewPosition(int newPosition) {
        Player currentPlayer = getCurrentPlayer();
        boolean hasPassedStart = false;

        // This will break if you have a board of size n and dice that can roll higher than 2*n.
        if (newPosition > fields.length - 1) {
            newPosition = newPosition - fields.length;
            currentPlayer.addBalance(4000);
            hasPassedStart = true;
        }
        currentPlayer.setPosition(newPosition);
        return hasPassedStart;
    }

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
        int i = 0;
        for (Player p : getPlayers()) {
            if (!p.isBankruptThisTurn()) {
                i++;
            }
        }
        return i < 2;
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
        for (Player p : players) {
            ownershipMap.put(p, new ArrayList<>());
        }
    }

    public Deck getDeck() {
        return deck;
    }

// TODO Languagecontroller somewhere else
//    public String getMessage(String key) {
//        return languageController.getMessage(key);
//    }


    public boolean removeBankruptPlayers() {
        boolean playerHasBeenRemoved = false;
        for (Player p : players) {
            if (p.isBankruptThisTurn()) {
                p.setHasLost(true);
                sellAllFields(p);
                playerHasBeenRemoved = true;
            }
        }
        return playerHasBeenRemoved;
    }
    public void sellAllFields(Player p){
        List<RentableField> ownedFields = ownershipMap.get(p);

        for (RentableField ownedField : ownedFields) {
            ownedField.setOwner(null);
        }
        ownedFields.clear();
    }

    void jailPlayer() {
        Player currentPlayer = getCurrentPlayer();
        Field[] fields = getFields();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (field instanceof Jail) {
                currentPlayer.setPosition(i);
                currentPlayer.setJailed(true);
                return;
            }
        }
        throw new IllegalArgumentException("There is no jail, so you can't use goToJail square");
        //TODO check if it works
        // Could we make gameboard somehow recieve an int so we can control how big it is for testing?
    }
}