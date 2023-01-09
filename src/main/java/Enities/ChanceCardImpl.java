package Enities;

import Controller.UserIO;
import Enities.ChanceCards.ChanceAction;

import Enities.Fields.Brewery;
import Enities.Fields.Ferry;
import Enities.Fields.Field;
import Language.Message;


public class ChanceCardImpl implements ChanceAction {
    private final GameBoard gameBoard;
    private final UserIO userIO;

    public ChanceCardImpl(GameBoard gameBoard, UserIO userIO) {
        this.gameBoard = gameBoard;
        this.userIO = userIO;
    }

    @Override
    public void getOutOfJail() {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        currentPlayer.addGetOutOfJailCard();
    }

    @Override
    public void changeBal(int amount) {
        gameBoard.getCurrentPlayer().addBalance(amount);
    }

    @Override
    public void changeBalConditional(int amount, int condition) {
        String playerName = gameBoard.getCurrentPlayer().getName();
        if (gameBoard.totalPlayerValue(gameBoard.getCurrentPlayer()) < condition) {
            userIO.showMessage(Message.giftToPoorPlayerCard(playerName, amount));
            changeBal(amount);
        } else {
            userIO.showMessage(Message.noGiftToRichPlayerCard(playerName, amount));
        }
    }

    @Override
    public void changeBalFromPlayers(int amount) {
        for (Player p : gameBoard.getPlayers()) {
            changeBal(amount);
            gameBoard.getCurrentPlayer().addBalance(amount);
            p.addBalance(-amount);
        }
    }

    @Override
    public void moveToField(int index) {
        gameBoard.getCurrentPlayer().setPosition(index);
        //TODO make sure they also get a turn
    }

    @Override
    public void moveSpaces(int spaces) {
        gameBoard.movePlayer(spaces);
    }

    @Override
    public void moveToFerry() {
        Field[] fields = gameBoard.getFields();
        int pos = gameBoard.getCurrentPlayer().getPosition();
        while (!(fields[pos] instanceof Ferry)) {
            pos++;
            if (pos == 40) pos = 0;
        }
        gameBoard.getCurrentPlayer().setPosition(pos);
    }

    @Override
    public void payPerProperty(int perHouse, int perHotel) {

        int houses = gameBoard.getCurrentPlayer().getHouses();
        int hotels = gameBoard.getCurrentPlayer().getHotels();

        gameBoard.getCurrentPlayer().addBalance(-((houses * perHouse) + (hotels * perHotel)));
    }

    @Override
    public void goToJail() {
        gameBoard.getCurrentPlayer().setJailed(true);
        gameBoard.getCurrentPlayer().setPosition(10);
        //TODO check if it works
        // Could we make gameboard somehow receive an int so we can control how big it is for testing?
    }

    @Override
    public void moveToBrewery() {
        Field[] fields = gameBoard.getFields();
        int pos = gameBoard.getCurrentPlayer().getPosition();
        while (!(fields[pos] instanceof Brewery)) {
            pos++;
            if (pos == 40) pos = 0;
        }
        gameBoard.getCurrentPlayer().setPosition(pos);
    }

    @Override
    public void printDescription(String description) {
        String playerName = gameBoard.getCurrentPlayer().getName();
        userIO.showMessage(Message.chanceCard(playerName, description));
    }
}
