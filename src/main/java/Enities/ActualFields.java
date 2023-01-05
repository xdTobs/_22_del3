package Enities;

import Enities.ChanceCards.ChanceCard;
import Enities.Fields.*;
import Language.LanguageController;
import Language.LanguageHandler;
import View.View;

public class ActualFields implements FieldAction {
    GameBoard gameBoard;
    View view;


    public ActualFields(GameBoard gameBoard, View view) {
        this.gameBoard = gameBoard;
        this.view = view;

    }

    private boolean wantToBuyPrompt(RentableField field) {
        LanguageController lc = gameBoard.getLanguageController();
        String yesOrNo = view.promptPlayer(new String[]{lc.getMessage("yes"), lc.getMessage("no")}, gameBoard.getCurrentPlayer().getName() + lc.getMessage("wantToBuyPrompt1") + " " + field.getName() + lc.getMessage("wantToBuyPrompt2"));
        return yesOrNo.equals(lc.getMessage("yes"));
    }

    @Override
    public Field streetAction(Street street) {
        LanguageController lc = gameBoard.getLanguageController();

        // If the street is owned by the bank, the player can buy it.
        Field boughtField = null;
        if (street.getOwner().equals("Bank") && wantToBuyPrompt(street)) {
            boughtField = buyEmptyStreet(street);
        } else {
            streetPayRentToOwner(street);
        }
        return boughtField;
    }

    public RentableField buyEmptyStreet(RentableField street) {
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

        if (houseOwner != null) {

            if (street.getHouses() == 0 && streetPlayerOwnsPair(street)) {
                rent *= 2;
            }

            houseOwner.addBalance(rent);
            gameBoard.getCurrentPlayer().addBalance(-rent);
        }
    }

    @Override
    public boolean streetPlayerOwnsPair(Street street) {
        boolean playerOwnsPair = true;
        for (int i : street.getPairIndexes()) {
            Street pairStreet = (Street) gameBoard.getFields()[i];
            if (!pairStreet.getOwner().equals(street.getOwner())) {
                playerOwnsPair = false;
            }
        }
        return playerOwnsPair;
    }

    @Override
    public void taxAction(Tax tax) {

        if (tax.getPercentPrice() > 0 && payPercentPrompt(tax)) {
            gameBoard.getCurrentPlayer().addBalance((int) -(gameBoard.totalPlayerValue(gameBoard.getCurrentPlayer()) * 0.1));
            return;
        }
        gameBoard.getCurrentPlayer().addBalance(-tax.getPrice());
    }

    private boolean payPercentPrompt(Tax tax) {
        LanguageController lc = gameBoard.getLanguageController();
        String valOrPercent = view.promptPlayer(new String[]{String.valueOf(tax.getPrice()), tax.getPercentPrice() + "%" + lc.getMessage("playerTotalValue")}, lc.getMessage("taxPrompt"));
        return valOrPercent.equals(String.valueOf(tax.getPrice()));
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
        Field boughtField = null;
        if (ferry.getOwner().equals("Bank") && wantToBuyPrompt(ferry)) {
            boughtField = buyEmptyStreet(ferry);
        } else {
            ferryPayRent(ferry);
        }
        return boughtField;
    }

    private void ferryPayRent(Ferry ferry) {

        Player[] players = gameBoard.getPlayers();
        String houseOwnerName = ferry.getOwner();
        Player houseOwner = null;
        for (Player player : players) {
            if (player.getName().equals(houseOwnerName)) {
                houseOwner = player;
            }
        }

        if (houseOwner != null) {
            int ferrysOwned = ferryPlayerOwns(ferry);
            int rent = ferry.getRent(ferrysOwned - 1);
            houseOwner.addBalance(rent);
            gameBoard.getCurrentPlayer().addBalance(-rent);
        }
    }

    public int ferryPlayerOwns(Ferry ferry) {
        int count = 0;
        for (int i : ferry.getPairIndexes()) {
            Ferry ferryCounter = (Ferry) gameBoard.getFields()[i];
            if (ferryCounter.getOwner().equals(gameBoard.getCurrentPlayer().getName()))
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
        LanguageController lc = gameBoard.getLanguageController();
        Field boughtField = null;
        if (brewery.getOwner().equals("Bank") && wantToBuyPrompt(brewery)) {
            boughtField = buyEmptyStreet(brewery);
        } else {
            breweryPayRent(brewery);
        }
        return boughtField;
    }

    public void breweryPayRent(Brewery brewery) {
        int diceSum = gameBoard.getDiceCup().getSum();
        Player[] players = gameBoard.getPlayers();
        String houseOwnerName = brewery.getOwner();
        Player houseOwner = null;
        for (Player player : players) {
            if (player.getName().equals(houseOwnerName)) {
                houseOwner = player;
            }
        }


        int rent = brewery.getRent(brewery.getHouses());
        if (houseOwner != null) {


            houseOwner.addBalance(rent * diceSum);
            gameBoard.getCurrentPlayer().addBalance(-rent * diceSum);
        }
    }

}
