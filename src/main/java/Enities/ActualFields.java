package Enities;

import Enities.ChanceCards.ChanceCard;
import Enities.Fields.*;
import Language.LanguageController;
import View.View;

import java.util.ArrayList;
import java.util.List;

public class ActualFields implements FieldAction {
    GameBoard gameBoard;
    View view;

    public ActualFields(GameBoard gameBoard, View view) {
        this.gameBoard = gameBoard;
        this.view = view;

    }

    private boolean wantToBuyPrompt(RentableField field) {
        String yesOrNo = view.promptPlayer(new String[]{gameBoard.getMessage("yes"), gameBoard.getMessage("no")}, gameBoard.getCurrentPlayer().getName() + gameBoard.getMessage("wantToBuyPrompt1") + " " + field.getName() + gameBoard.getMessage("wantToBuyPrompt2"));
        return yesOrNo.equals(gameBoard.getMessage("yes"));
    }


    @Override
    public Field streetAction(Street street) {

        // If the street is owned by the bank, the player can buy it.
        Field boughtField = null;
        if (street.getOwner().equals("Bank") && wantToBuyPrompt(street)) {
            boughtField = buyEmptyStreet(street);
        } else {
            streetPayRentToOwner(street);
            view.showMessage(gameBoard.getMessage("payRent"));
        }
        return boughtField;
    }

    //TODO figure out where this goes
    private void buyHouseProcess() {
        while (wantToBuyHouse()) {
            List<RentableField> ownedFields = gameBoard.getOwnershipMap().get(gameBoard.getCurrentPlayer());
            List<Street> ownedStreets = new ArrayList<>();
            for (RentableField ownedField : ownedFields) {
                if (ownedField instanceof Street street && street.getHouses() < 6)
                    ownedStreets.add(street);
            }

            String[] choices = new String[ownedStreets.size() + 1];
            for (int i = 0; i < ownedStreets.size(); i++) {
                choices[i + 1] = ownedStreets.get(i).getName() + " " + ownedStreets.get(i).getHousePrice() + " " + gameBoard.getMessage("DKK per");
            }
            choices[0] = gameBoard.getMessage("noMoreHouses");
            String message = gameBoard.getMessage("selectHouse");
            String selection = view.promptPlayer(choices, message);
            if (selection.equals(gameBoard.getMessage("noMoreHouses")))
                return;
            for (Street ownedStreet : ownedStreets) {
                if (selection.equals(ownedStreet.getName() + " " + ownedStreet.getHousePrice() + " " + gameBoard.getMessage("DKK per"))) {
                    buyHouse(ownedStreet);
                    break;
                }
            }
        }
    }

    private void buyHouse(Street street) {
        street.setHouses(street.getHouses() + 1);
        gameBoard.getCurrentPlayer().addBalance(-street.getHousePrice());
    }

    private boolean wantToBuyHouse() {
        String[] choices = new String[]{gameBoard.getMessage("yes"), gameBoard.getMessage("no")};
        String message = gameBoard.getMessage("wantToBuyHouse");
        String wantToBuyHouse = view.promptPlayer(choices, message);
        return (wantToBuyHouse.equals(gameBoard.getMessage("yes")));
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
        for (int i : street.getPair().getFieldIds()) {
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
            gameBoard.getCurrentPlayer().addBalance(-tax.getPrice());
            return;
        }
        gameBoard.getCurrentPlayer().addBalance((int) -(gameBoard.totalPlayerValue(gameBoard.getCurrentPlayer()) * 0.1));
    }

    private boolean payPercentPrompt(Tax tax) {
        String valOrPercent = view.promptPlayer(new String[]{String.valueOf(tax.getPrice()), tax.getPercentPrice() + "%" + gameBoard.getMessage("playerTotalValue")}, gameBoard.getMessage("taxPrompt"));
        return valOrPercent.equals(String.valueOf(tax.getPrice()));
    }

    @Override
    public void goToJailAction(GoToJail goToJail) {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        view.showMessage(gameBoard.getMessage("goToJailMsg"));
        currentPlayer.setPosition(10);
        currentPlayer.setJailed(true);
    }

    @Override
    public void jailAction(Jail jail){
        Player currentPlayer = gameBoard.getCurrentPlayer();
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
            int rent = ferry.getRent(ferrysOwned);
            houseOwner.addBalance(rent);
            gameBoard.getCurrentPlayer().addBalance(-rent);
        }
    }

    public int ferryPlayerOwns(Ferry ferry) {
        int count = 0;
        for (int i : ferry.getPair().getFieldIds()) {
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
