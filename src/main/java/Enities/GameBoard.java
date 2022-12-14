package Enities;

import Enities.ChanceCards.Deck;
import Enities.Fields.*;

/**
 * Class for the game board.
 * Contains all the fields and the chance deck and diceCup.
 * This is the model.
 */
public class GameBoard {
    private Player[] players;
    private int playerTurn;
    private final DiceCup diceCup = new DiceCup();
    private final Field[] fields = new Field[24];
    private final Deck deck;

    /**
     * Instantiates a new Game board.
     */
    public GameBoard() {
        for (int i = 0; i < fields.length; i++) {
            if (i == 0) {
                this.fields[i] = new Start();
            } else if ((i + 3) % 6 == 0) {
                // Chance field. 3, 9, 15, 21. Every sixth with an offset of three is chance field.
                this.fields[i] = new ChanceField(i);
            } else if (i == 6) {
                // Jail field
                this.fields[i] = new Jail();
            } else if (i == 12) {
                // Parking
                this.fields[i] = new Parking();
            } else if (i == 18) {
                // Go to jail
                this.fields[i] = new GoToJail();
            } else {
                this.fields[i] = new Street(i);
            }
        }

        this.deck = new Deck(fields);
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
        field.executeFieldAction(this);
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
        if (newPosition > 23) {
            newPosition = newPosition - 24;
            currentPlayer.addBalance(2);
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
        for (Player player : players) {
            if (player.getBalance() <= 0) {
                return true;
            }
        }
        return false;
    }

    public String findWinner() {
        String winner = players[0].getName();
        int winnerBalance = players[0].getBalance();
        for (int i = 1; i < players.length; i++) {

            if (players[i].getBalance() == winnerBalance) {
                winner = winner + " & " + players[i].getName();
            }
            if (players[i].getBalance() > winnerBalance) {
                winner = players[i].getName();
                winnerBalance = players[i].getBalance();
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


    public Street getStreet(int i) {
        Field field = getField(i);
        if (field instanceof Street street) {
            return street;
        }
        throw new IllegalArgumentException("You can not call this method with a position that is not the position of a street.");
    }


    public void createPlayers(int playerCount) {
        Player[] players = new Player[playerCount];
        for (int j = 0; j < playerCount; j++) {
            int bal = 0;
            switch (playerCount) {
                case (2):
                    bal = 20;
                    break;
                case (3):
                    bal = 18;
                    break;
                case (4):
                    bal = 16;
                    break;
            }

            players[j] = new Player("Player" + Math.addExact(j, 1), bal);
        }
        setPlayers(players);
    }

    private void setPlayers(Player[] players) {
        this.players = players;
    }

    public Deck getDeck() {
        return deck;
    }
}
