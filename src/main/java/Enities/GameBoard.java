package Enities;

import ChanceCards.ChanceCard;
import ChanceCards.Deck;
import Enities.Fields.*;
import Language.LanguageHandler;

public class GameBoard {
    private final int playerCount = 2;
    private final Player[] players = new Player[playerCount];
    private int playerTurn;
    private final DiceCup diceCup = new DiceCup();
    private final Field[] fields = new Field[24];
    private final Deck deck;
    private ChanceCard latestChanceCard;

    /**
     * The Cards, chance.
     */

    public GameBoard() {
        for (int i = 0; i < fields.length; i++) {
            if (i == 0) {
                this.fields[i] = new Start();
            } else if ((i + 3) % 6 == 0) {
                // ChanceCards.Chance field. 3, 9, 15, 21. Every sixth with an offset of three is chance field.
                this.fields[i] = new Chance(i);
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

        this.players[0] = new Player(LanguageHandler.getPlayerName1(), 20);
        this.players[1] = new Player(LanguageHandler.getPlayerName2(), 20);
        this.deck = new Deck(this);
    }

    public Field getField(int position) {
        return fields[position];
    }


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

    /**
     * @return Returns true if player has passed go.
     */
    public boolean currentPlayerIsOnChanceField() {
        return getFieldOfCurrentPlayer() instanceof Chance;
    }

    private Field getFieldOfCurrentPlayer() {
        Player currentPlayer = getCurrentPlayer();
        int position = currentPlayer.getPosition();
        return getField(position);
    }

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

    public void payFine(Player currentPlayer) {
        if (currentPlayer.getGetOutOfJailCards() > 0) {
            currentPlayer.setGetOutOfJailCards(currentPlayer.getGetOutOfJailCards() - 1);
        } else {
            currentPlayer.addBalance(-1);
        }
        currentPlayer.setJailed(false);
    }

    public Player[] getPlayers() {
        return players;
    }

    public void pullNewChanceCard() {
        latestChanceCard = deck.pullCard();
    }


    public void nextPlayer() {
        if (playerTurn >= players.length - 1) {
            playerTurn = 0;
        } else {
            playerTurn++;
        }
    }

    public boolean isGameover() {
        for (Player player : players) {
            if (player.getBalance() <= 0) {
                return true;
            }
        }
        return false;
    }

    public ChanceCard getLatestChanceCard() {
        return latestChanceCard;
    }


    public Street getStreet(int i) {
        Field field = getField(i);
        if (field instanceof Street street) {
            return street;
        }
        throw new IllegalArgumentException("You can not call this method with a position that is not the position of a street.");
    }
}
