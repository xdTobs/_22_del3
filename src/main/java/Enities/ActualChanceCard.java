package Enities;

import Enities.ChanceCards.ChanceAction;

public class ActualChanceCard implements ChanceAction {
    GameBoard gameBoard;
    public ActualChanceCard(GameBoard gameBoard){
        this.gameBoard = gameBoard;
    }
    @Override
    public void getOutOfJail() {
        System.out.println("test");
            /*Player currentPlayer = gameBoard.getCurrentPlayer();
            currentPlayer.addGetOutOfJailCard();*/
    }
}
