package Enities;

import Enities.ChanceCards.ChanceAction;

import Enities.Fields.Brewery;
import Enities.Fields.Ferry;
import Enities.Fields.Field;
import Controller.View;


public class ActualChanceCard implements ChanceAction {
    GameBoard gameBoard;
    View view;

    public ActualChanceCard(GameBoard gameBoard, View view) {
        this.gameBoard = gameBoard;
        this.view = view;
    }

    @Override
    public void getOutOfJail() {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        currentPlayer.addGetOutOfJailCard();
    }

    @Override
    public void changeBal(int i) {
        gameBoard.getCurrentPlayer().addBalance(i);
    }

    @Override
    public void changeBalConditional(int amount, int condition) {
        if (gameBoard.totalPlayerValue(gameBoard.getCurrentPlayer()) < condition) {
            view.showMessage(gameBoard.getCurrentPlayer().getName() + gameBoard.getMessage("succesfulConditionChanceCard") + amount + " DKK");
        } else {
            view.showMessage(gameBoard.getCurrentPlayer().getName() + gameBoard.getMessage("unSuccesfulConditionChanceCard"));
        }
    }

    @Override
    public void changeBalFromPlayers(int amount) {
        for (Player p : gameBoard.getPlayers()) {
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
        int playerPos = gameBoard.getCurrentPlayer().getPosition();
        if (playerPos + spaces > 0 && playerPos + spaces < gameBoard.getFields().length) {
            gameBoard.getCurrentPlayer().setPosition(playerPos + spaces);
        } else if (playerPos + spaces < 0) {
            gameBoard.getCurrentPlayer().setPosition(gameBoard.getFields().length + playerPos + spaces);
        } else {
            gameBoard.getCurrentPlayer().setPosition(gameBoard.getFields().length - playerPos + spaces);
        }
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
        // Could we make gameboard somehow recieve an int so we can control how big it is for testing?
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
    public void printDesc(String desc) {
        view.showMessage(desc);
    }

}
