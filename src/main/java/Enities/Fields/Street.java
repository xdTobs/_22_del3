package Enities.Fields;

import Enities.GameBoard;
import Enities.Player;
import Language.LanguageHandler;

public class Street extends RentableField {


    public Street(String s) {
        super(s);
    }

    @Override
    public int getPrice() {
        return super.getPrice();
    }

    @Override
    public int getHousePrice() {
        return super.getHousePrice();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public int getRent(int i) {
        return super.getRent(i);
    }

    @Override
    public void executeFieldAction(GameBoard gameBoard) {
        // If the street is owned by the bank, the player can buy it.
        if (getOwner().equals("Bank")) {
            buyEmptyStreet(gameBoard);
        } else {
            payRentToOwner(gameBoard);
        }
    }

    private void buyEmptyStreet(GameBoard gameBoard) {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        setOwnerName(currentPlayer.getName());
        currentPlayer.addBalance(-getPrice());
    }

    private void payRentToOwner(GameBoard gameBoard) {

        // If the street is owned by another player, the player has to pay rent.
        // We need to find the owner, not just the name, so we can add the rent to him.
        Player[] players = gameBoard.getPlayers();
        String houseOwnerName = getOwner();
        Player houseOwner = null;
        int rent = getRent(0);
        for (Player player : players) {
            if (player.getName().equals(houseOwnerName)) {
                houseOwner = player;
            }
        }
        // If you land on your own house, you don't have to pay rent. But we can ignore handling that, because paying yourself $2 dollars makes no difference. The gameover check comes much later.
        assert houseOwner != null;
        houseOwner.addBalance(rent);
        gameBoard.getCurrentPlayer().addBalance(-rent);
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


    public int getPositionOfPairStreet() {
        // If we are on street 1, position 2 is sibling.
        // There are always 2 streets, then a chance or a corner square.
        // So we can just use modulo to calculate the position of the sibling.
        // This won't work in real monopoly, but we don't have to worry about that.
        if ((this.getPosition() - 1) % 3 == 0) {
            return this.getPosition() + 1;
        } else if ((this.getPosition() - 2) % 3 == 0) {
            return this.getPosition() - 1;
        } else {
            throw new IllegalArgumentException("This is not a pair position, and probably not a street");
        }
    }


}
