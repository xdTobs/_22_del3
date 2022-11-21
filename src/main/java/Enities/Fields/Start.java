package Enities.Fields;

import Enities.GameBoard;
import Enities.Player;

public class Start extends Field {
    private String name;
    private String description;


    public Start() {
        super(0);
        name = "Start";
        description = "This is start";
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void executeFieldAction(GameBoard gameBoard) {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        //TODO should it update balce if you land on start? shouldnt it just be when passed?
        currentPlayer.addBalance(2);
    }
}
