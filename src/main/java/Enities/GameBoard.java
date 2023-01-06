package Enities;

import Enities.ChanceCards.Deck;
import Enities.DiceCup.DiceCup;
import Enities.Fields.*;
import Language.LanguageController;

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
    private final LanguageController languageController;
    private Deck deck;
    private Player[] players;
    private int playerTurn;
    private ActualChanceCard acc;
    private ActualFields actualFields;

    public GameBoard(LanguageController languageController, Deck deck, DiceCup diceCup, Field[] fields) {
        this.deck = deck;
        this.diceCup = diceCup;
        this.languageController = languageController;
        this.fields = fields;
    }

    public static GameBoard setup(LanguageController languageController, DiceCup diceCup, String csvPath) {
        var inputStream = GameBoard.class.getClassLoader().getResourceAsStream(csvPath);
        if (inputStream == null) {
            throw new IllegalStateException("Inputstream should not be null");
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        // First row is keys. Type, position, rent etc.
        Stream<String> iter = bufferedReader.lines().skip(1);
        List<Field> temp = iter.map(line -> {
            String[] key = line.split(",");
            switch (key[2].trim()) {
                case ("street") -> {
                    return new Street(line);
                }
                case ("tax") -> {
                    return new Tax(line);
                }
                case ("jail") -> {
                    return new Jail(line);
                }
                case ("gotoJail") -> {
                    return new GoToJail(line);
                }
                case ("chance") -> {
                    return new ChanceField(line);
                }
                case ("refugee") -> {
                    return new Parking(line);
                }
                case ("start") -> {
                    return new Start(line);
                }
                case ("brewery") -> {
                    return new Brewery(line);
                }
                case ("ferry") -> {
                    return new Ferry(line);
                }
                default -> throw new IllegalStateException("Unexpected value: " + key[2].trim());
            }
        }).toList();

        Field[] fields = new Field[temp.size()];
        temp.toArray(fields);
        // TODO
        //  we need to figure out some way to make simpler tests.
        //  Can we find a way to make the board only 5 square and then test jail on that board?
        //  Can we do that without changing current code to much?
        // We only make fieldPairs if it is the size of the original matador board.
        // We do this so we can run tests with other board sizes.
        fields = initFieldPairs(fields);
        GameBoard gameBoard = new GameBoard(new LanguageController("english"), new Deck(), new DiceCup(), fields);
        return gameBoard;
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


    public ActualChanceCard getAcc() {
        return acc;
    }

    public void setAcc(ActualChanceCard acc) {
        this.acc = acc;
    }

    public HashMap<Player, List<RentableField>> getOwnershipMap() {
        return ownershipMap;
    }

    public ActualFields getActualFields() {
        return actualFields;
    }

    public void setActualFields(ActualFields actualFields) {
        this.actualFields = actualFields;
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
        if (boughtField instanceof RentableField rentableBoughtField) {
            ownershipMap.get(currentPlayer).add(rentableBoughtField);
        }
    }

    public int totalPlayerValue(Player p) {
        int res = 0;
        List<RentableField> playerOwnedFields = ownershipMap.get(p);
        for (RentableField field : playerOwnedFields) {
            res += field.getPrice();
            int housesprice = field.getHousePrice();
            int houses = field.getHouses();
            res += houses * housesprice;
        }
        res += p.getBalance();
        return res;
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
        if (newPosition > fields.length - 1) {
            newPosition = newPosition - fields.length;
            currentPlayer.addBalance(4000);
            hasPassedStart = true;
        }
        currentPlayer.setPosition(newPosition);
        return hasPassedStart;
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
        return players.length == 1;
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
    public String getMessage(String key) {
        return languageController.getMessage(key);
    }

    public void isPlayerBankrupt() {
        int i = 0;
        while (i < players.length) {
            if (players[i].getBalance() < 0) {
                removePlayer(i);
                break;
            }
            i++;
        }
    }

    private void removePlayer(int i) {
        List<Player> newPlayerArray = new ArrayList<>();
        for (int j = 0; j < players.length; j++) {
            if (!(i == j)) newPlayerArray.add(players[j]);
            else players[j].setBalance(-99999);
        }
        players = newPlayerArray.toArray(new Player[3]);
        int l = players[0].getBalance();
    }
}