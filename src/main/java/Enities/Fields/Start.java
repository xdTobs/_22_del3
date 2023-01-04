package Enities.Fields;

import Enities.GameBoard;
import Enities.Player;

public class Start extends Field {



    public Start(String s) {
        super(s);
    }

    public String getName() {
        return super.getName();
    }



    @Override
    public void executeFieldAction(GameBoard gameBoard) {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        //TODO should it update balce if you land on start? shouldnt it just be when passed?
        //currentPlayer.addBalance(2);
    }
}
