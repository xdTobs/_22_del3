package Enities;

import Enities.ChanceCards.ChanceAction;

import View.View;

public class ActualChanceCard implements ChanceAction {
    GameBoard gameBoard;
    View view;
    public ActualChanceCard(GameBoard gameBoard,View view){
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

    }

    @Override
    public void changeBalConditional(int amount, int condition) {

    }

    @Override
    public void changeBalFromPlayers(int amount) {

    }

    @Override
    public void moveToField(int index) {

    }

    @Override
    public void moveSpaces(int spaces) {

    }

    @Override
    public void moveToFerry() {

    }

    @Override
    public void payPerProperty(int perHouse, int perHotel) {

    }

    @Override
    public void goToJail() {

    }

    @Override
    public void moveToBrewery() {

    }

    @Override
    public void printDesc(String desc) {
        view.showMessage(desc);
    }

}
