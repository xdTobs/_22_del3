package Enities.Fields;

import Enities.GameBoard;
import Enities.Player;
import Language.LanguageHandler;

public class Street extends Field {
    private int rent;


    private String owner = "Banke";
    String name;

    public Street(int position) {
        super(position);
        this.rent = calculateRent(position);
        name = LanguageHandler.getFieldName(position);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void executeFieldAction(GameBoard gameBoard) {


        Player currentPlayer = gameBoard.getCurrentPlayer();
        Player[] players = gameBoard.getPlayers();
        int rent = getRent();
        if (getOwner().equals("Bank")) {
            setOwner(currentPlayer.getName());
            currentPlayer.addBalance(-rent);
        } else {
            // TODO Test if this works. I think it should.
            String houseOwnerName = getOwner();
            Player houseOwner = null;
            for (Player player : players) {
                if (player.getName().equals(houseOwnerName)) {
                    houseOwner = player;
                }
            }
            assert houseOwner != null;
            // If you land on your own house, you don't have to pay rent. But we can ignore handling that, because paying yourself $2 dollars makes no difference. The gameover check comes much later.
            houseOwner.addBalance(rent);
            currentPlayer.addBalance(-rent);
        }
    }

    private int calculateRent(int i) {
        if (i < 6) {
            return 1;
        } else if (i < 12) {
            return 2;
        } else if (i < 18) {
            return 3;
        } else if (i < 21) {
            return 4;
        } else {
            return 5;
        }
    }

    public int getRent() {
        return rent;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}
