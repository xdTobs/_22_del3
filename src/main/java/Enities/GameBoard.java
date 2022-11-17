package Enities;

import ChanceCards.ChanceCard;
import ChanceCards.Deck;
import Enities.Fields.*;
import Language.LanguageHandler;

public class GameBoard {
    private int playerCount = 2;
    private Player[] players = new Player[playerCount];
    private int playerTurn;
    private DiceCup diceCup = new DiceCup();
    private Field[] fields = new Field[24];
    private Deck cards = new Deck();

    /**
     * The Cards, chance.
     */

    public GameBoard() {
        for (int i = 0; i < fields.length; i++) {
            if (i == 0) {
                this.fields[i] = new Start(i);
            } else if ((i + 3) % 6 == 0) {
                // ChanceCards.Chance field. 3, 9, 15, 21. Every sixth with an offset of three is chance field.
                this.fields[i] = new Chance(i);
            } else if (i == 5) {
                // Jail field
                this.fields[i] = new Jail();
            } else if (i == 11) {
                // Parking
                this.fields[i] = new Parking();
            } else if (i == 17) {
                // Go to jail
                this.fields[i] = new GoToJail(i);
            } else {
                this.fields[i] = new Street(i);
            }
        }

        this.players[0] = new Player(LanguageHandler.getPlayerName1(), 20);
        this.players[1] = new Player(LanguageHandler.getPlayerName2(), 20);
    }

    public Field getField(int position) {
        return fields[position];
    }

    public void onStart() {
        gui_controller.showMessage(LanguageHandler.passedStartMsg());
    }

    public void onRefuge() {
        gui_controller.showMessage(LanguageHandler.parkingMsg());
    }

    /**
     * We have landed on street.
     *
     * @param street        the street
     * @param currentPlayer the current player
     */


    /**
     * We have landed on chance, we get a card.
     *
     * @param currentPlayer the current player
     */
    // TODO implement action is an abstract method in chance, that is called here.
    public void onChance(Player currentPlayer) {
        ChanceCard chanceCard = cards.pullCard();
        chanceCard.executeCardAction(currentPlayer);
//        gui_controller.displayText(currentPlayer.getName() + " " + LanguageHandler.chanceCardMsg() + chanceCard.getDesc());
    }

    /**
     * We have landed on jail, we need to pay money next turn, unless we have special card.
     *
     * @param currentPlayer the current player
     */
    public void onJail(Player currentPlayer) {
        currentPlayer.setJailed(true);
    }

    // When the player lands on a field, we find out which one here, and then we take action according to field type.
    public void fieldAction(Player currentPlayer) {
        var playerPosition = currentPlayer.getPosition();
        var field = getField(playerPosition);
        field.executeFieldAction(this);
//        if (field instanceof Street street) {
//            street.executeFieldAction(this);
//        } else if (field instanceof Chance) {
//            onChance(currentPlayer);
//        } else if (field instanceof Jail) {
//            onJail(currentPlayer);
//        } else if (field instanceof GoToJail goToJail) {
//            goToJail.executeFieldAction(this);
//        } else if (field instanceof Start) {
//            onStart();
//        } else if (field instanceof Parking) {
//            onRefuge();
//        }
    }

    public Field[] getFields() {
        return fields;
    }

    public void movePlayer(int pos, Player currentPlayer) {
        currentPlayer.setPosition(pos);
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
            currentPlayer.setJailed(false);
        } else {
            currentPlayer.addBalance(-1);
            currentPlayer.setJailed(false);
        }
    }

    public Player[] getPlayers() {
        return players;
    }
}
