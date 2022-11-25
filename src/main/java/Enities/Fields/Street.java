package Enities.Fields;

import Enities.GameBoard;
import Enities.Player;
import Language.LanguageHandler;

public class Street extends Field {
    final private int rent;
    private String ownerName = "Bank";
    final private String name;

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
        currentPlayer.addBalance(-getRent());
    }

    private void payRentToOwner(GameBoard gameBoard) {

        // If the street is owned by another player, the player has to pay rent.
        // We need to find the owner, not just the name, so we can add the rent to him.
        Player[] players = gameBoard.getPlayers();
        String houseOwnerName = getOwner();
        Player houseOwner = null;
        int rent = getRent();
        for (Player player : players) {
            if (player.getName().equals(houseOwnerName)) {
                houseOwner = player;
            }
        }
        // We need to check if the house owner owns both streets. If he does you have to pay double rent.
        int positionOfPairStreet = this.getPositionOfPairStreet();
        Street pairStreet = gameBoard.getStreet(positionOfPairStreet);
        // Added this to make intellij stop complaining. It should never be null, because we are only checking for the pair street if the street is owned.
        if (houseOwner != null) {
            // We double the rent if the house owner owns both streets.
            if (houseOwner.getName().equals(pairStreet.getOwner())) {
                rent *= 2;
            }
        } else {
            throw new IllegalStateException("House owner is null");
        }

        // If you land on your own house, you don't have to pay rent. But we can ignore handling that, because paying yourself $2 dollars makes no difference. The gameover check comes much later.
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

    public int getRent() {
        return rent;
    }

    public String getOwner() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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
