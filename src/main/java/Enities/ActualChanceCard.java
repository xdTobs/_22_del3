package Enities;

import Enities.ChanceCards.ChanceAction;
import View.GUI_View;

public class ActualChanceCard implements ChanceAction {
    GameBoard gameBoard;
    GUI_View view;
    public ActualChanceCard(GameBoard gameBoard,GUI_View view){
        this.gameBoard = gameBoard;
        this.view = view;

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
    public void changeBalConditional(int amount, int condition, String desc) {

    }

    @Override
    public void changeBalFromPlayers(int amount, String desc) {

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

    @Override
    public void moveToBrewery(String desc) {

    }
}
