package Enities;

import Enities.ChanceCards.ChanceCard;
import Enities.Fields.*;
import Language.LanguageHandler;
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
           //TODO Prompt player here, need to get a reference to LanguageHandler.
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
    public int ferryPlayerOwns(Ferry ferry){
        int count=0;
        for (int i : ferry.getPairIndexes()){
            Ferry ferryCounter = (Ferry)gameBoard.getFields()[i];
            if(ferryCounter.getOwner().equals(gameBoard.getCurrentPlayer().getName()))
                count++;
        }
        return count;

    }

    @Override
    public void chanceFieldAction(ChanceField chanceField) {
        ChanceCard chanceCard = gameBoard.getDeck().getLatestChanceCard();
        gameBoard.getDeck().shuffleCards();
        chanceCard.executeCardAction(gameBoard.getAcc());
    }


    @Override
    public Field breweryAction(Brewery brewery) {
        return null;
    }

}
