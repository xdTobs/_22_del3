package entities;

import controller.UserIO;
import entities.chancecards.Deck;
import entities.dicecup.DiceCup;
import entities.dicecup.RandomDiceCup;
import entities.fields.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

/**
 * Class for the game board.
 * Contains all the fields and the chance deck and diceCup.
 * This is the model.
 */
public class GameBoard {


    private DiceCup randomDiceCup;
    private final HashMap<Player, List<RentableField>> ownershipMap;
    private final Field[] fields;
    private Deck deck;
    private final Player[] players;
    private int playerTurn;
    private final ChanceCardImpl chanceCardImpl;
    private final FieldImpl fieldImpl;
    private final List<Player> orderOfLosing = new ArrayList<>();

    public GameBoard(Field[] fields, UserIO userIO, Player[] players) {
//        this(new DiceCup(new Die[]{new TestDie(4), new TestDie(0)}),fields, userIO, players);
        this(new RandomDiceCup(), fields, userIO, players);
    }

    public GameBoard(DiceCup randomDiceCup, Field[] fields, UserIO userIO, Player[] players) {
        this(randomDiceCup, fields, Deck.setup(), userIO, players);
    }

    public GameBoard(DiceCup randomDiceCup, Field[] fields, Deck deck, UserIO userIO, Player[] players) {
        this.chanceCardImpl = new ChanceCardImpl(this, userIO);
        this.fieldImpl = new FieldImpl(this, userIO);
        this.deck = deck;
        this.randomDiceCup = randomDiceCup;
        this.fields = fields;
        this.players = players;
        this.ownershipMap = new HashMap<>();
        for (Player player : players) {
            ownershipMap.put(player, new ArrayList<>());
        }
    }

    public static GameBoard setup(Field[] fields, UserIO userIO, int playerCount) {
        String[] names = userIO.promptPlayerNames(playerCount);
        Player[] players = Player.setupPlayers(names);
        return new GameBoard(fields, userIO, players);
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
        // We do this so border that marks who owns fields and color of the field it self is not same color.
        // It is impossible to see border of red player on red field otherwise.
        // Magenta is changed because it is ugly otherwise.
        Color red = new Color(255, 80, 80);
        Color blue = new Color(80, 80, 255);
        Color yellow = new Color(235, 235, 20);
        Color magenta = new Color(235, 80, 235);
        List<FieldPair> fieldPairs = new ArrayList<>();
        fieldPairs.add(new FieldPair(blue, Color.WHITE, new int[]{1, 3}));
        fieldPairs.add(new FieldPair(Color.ORANGE, new int[]{6, 8, 9}));
        fieldPairs.add(new FieldPair(yellow, new int[]{11, 13, 14}));
        fieldPairs.add(new FieldPair(Color.GRAY, Color.WHITE, new int[]{16, 18, 19}));
        fieldPairs.add(new FieldPair(red, Color.WHITE, new int[]{21, 23, 24}));
        fieldPairs.add(new FieldPair(Color.WHITE, new int[]{26, 27, 29}));
        fieldPairs.add(new FieldPair(yellow, new int[]{31, 32, 34}));
        fieldPairs.add(new FieldPair(magenta, new int[]{37, 39}));
        fieldPairs.add(new FieldPair(Color.BLACK, Color.GREEN, new int[]{2, 7, 17, 22, 33, 36}));
        fieldPairs.add(new FieldPair(Color.LIGHT_GRAY, new int[]{4, 38}));
        fieldPairs.add(new FieldPair(blue, Color.WHITE, new int[]{5, 15, 25, 35}));
        fieldPairs.add(new FieldPair(Color.GRAY, Color.WHITE, new int[]{20}));
        fieldPairs.add(new FieldPair(Color.BLACK, new int[]{10}));
        fieldPairs.add(new FieldPair(Color.WHITE, Color.BLACK, new int[]{30}));
        fieldPairs.add(new FieldPair(red, Color.WHITE, new int[]{28, 12}));
        fieldPairs.add(new FieldPair(red, Color.WHITE, new int[]{0}));

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

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void setRandomDiceCup(DiceCup randomDiceCup) {
        this.randomDiceCup = randomDiceCup;
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
//        if (playerOwnedFields != null) {
        for (RentableField field : playerOwnedFields) {
            totalPlayerValue += field.getPrice();
            if (field instanceof Street street) {
                int housesprice = street.getHousePrice();
                int houses = street.getHouses();
                totalPlayerValue += houses * housesprice;
            }
        }

//        }
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
        return randomDiceCup;
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
        return movePlayer(randomDiceCup.getSum());
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
            if (p.hasNotLost()) {
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

    public String findLosers() {
        StringBuilder losers = new StringBuilder();
        for (Player player : orderOfLosing) {
            losers.append(player.getName()).append(", ");
        }
        return losers.toString();
    }


    public Deck getDeck() {
        return deck;
    }

    public boolean removeBankruptPlayers() {
        boolean playerHasBeenRemoved = false;
        for (Player p : players) {
            if (p.isBankruptThisTurn()) {
                orderOfLosing.add(p);
                p.setHasLost(true);
                sellAllFields(p);
                playerHasBeenRemoved = true;
            }
        }
        return playerHasBeenRemoved;
    }

    public void sellAllFields(Player p) {
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
    }

    public String getRemainingPlayerNames() {
        StringBuilder names = new StringBuilder();
        for (Player player : players) {
            if (player.hasNotLost()) {
                names.append(player.getName()).append(", ");
            }
        }
        return names.toString();
    }
}