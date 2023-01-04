package Enities;

import Enities.ChanceCards.ChanceCard;
import Enities.Fields.*;
import View.View;

public class ActualFields implements FieldAction {
    GameBoard gameBoard;
    View view;

    public ActualFields(GameBoard gameBoard, View view) {
        this.gameBoard = gameBoard;
        this.view = view;
    }

    @Override
    public Field streetAction(Street street) {
        // If the street is owned by the bank, the player can buy it.
        Field boughtField = null;
        if (street.getOwner().equals("Bank")) {
            boughtField = buyEmptyStreet(street);
        } else {
            streetPayRentToOwner(street);
        }
        return boughtField;
    }

    @Override
    public RentableField buyEmptyStreet(Street street) {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        street.setOwnerName(currentPlayer.getName());
        currentPlayer.addBalance(-street.getPrice());
        return street;
    }

    @Override
    public void streetPayRentToOwner(Street street) {
        // If the street is owned by another player, the player has to pay rent.
        // We need to find the owner, not just the name, so we can add the rent to him.
        Player[] players = gameBoard.getPlayers();
        String houseOwnerName = street.getOwner();
        Player houseOwner = null;
        int rent = street.getRent(street.getHouses());
        for (Player player : players) {
            if (player.getName().equals(houseOwnerName)) {
                houseOwner = player;
            }
        }
        // If you land on your own house, you don't have to pay rent. But we can ignore handling that, because paying yourself $2 dollars makes no difference. The gameover check comes much later.
        assert houseOwner != null;

        if(street.getHouses() == 0 &&streetPlayerOwnsPair(street)){
            rent*=2;
        }

        houseOwner.addBalance(rent);
        gameBoard.getCurrentPlayer().addBalance(-rent);

    }

    @Override
    public boolean streetPlayerOwnsPair(Street street) {
        boolean playerOwnsPair = true;
        for (int i : street.getPairIndexes()){
            Street pairStreet = (Street) gameBoard.getFields()[i];
            if (!pairStreet.getOwner().equals(street.getOwner())){
                playerOwnsPair = false;
            }
        }
        return playerOwnsPair;
    }

    @Override
    public void taxAction(Tax tax) {

    }

    @Override
    public void goToJailAction(GoToJail goToJail) {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        currentPlayer.setPosition(10);
        currentPlayer.setJailed(true);
        return;
    }

    @Override
    public Field ferryAction(Ferry ferry) {
        return null;
    }

    @Override
    public void chanceFieldAction(ChanceField chanceField) {
        ChanceCard chanceCard = gameBoard.getDeck().getLatestChanceCard();
        gameBoard.getDeck().shuffleCards();
        chanceCard.executeCardAction(gameBoard.getAcc());
    }

    @Override
    public void chanceAction(Chance chance) {
        ChanceCard chanceCard = gameBoard.getDeck().getLatestChanceCard();
        gameBoard.getDeck().shuffleCards();
        chanceCard.executeCardAction(gameBoard.getAcc());
    }

    @Override
    public Field breweryAction(Brewery brewery) {
        return null;
    }

}
