package Enities;

import Enities.ChanceCards.ChanceAction;

public class ActualChanceCard implements ChanceAction {
    GameBoard gameBoard;
    public ActualChanceCard(GameBoard gameBoard){
        this.gameBoard = gameBoard;
    }
    @Override
    public void getOutOfJail(String desc) {

            Player currentPlayer = gameBoard.getCurrentPlayer();
            currentPlayer.addGetOutOfJailCard();
    }

    @Override
    public void changeBal(int i, String desc) {

    }

    @Override
    public void changeBalConditional(int amout, int condition, String desc) {

    }

    @Override
    public void changeBalFromPlayers(int amout, String desc) {

    }

    @Override
    public void moveToField(int index, String desc) {

    }

    @Override
    public void moveSpaces(int spaces, String desc) {

    }

    @Override
    public void moveToFerry(String desc) {

    }

    @Override
    public void payPerProperty(int perHouse, int perHotel, String desc) {

    }

    @Override
    public void goToJail(String desc) {

    }
}
