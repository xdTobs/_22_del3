package Enities;

import Enities.ChanceCards.ChanceAction;

public class ActualChanceCard implements ChanceAction {
    GameBoard gameBoard;
    public ActualChanceCard(GameBoard gameBoard){
        this.gameBoard = gameBoard;
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
    public void changeBalConditional(int amout, int condition) {

    }

    @Override
    public void changeBalFromPlayers(int amout) {

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
}
