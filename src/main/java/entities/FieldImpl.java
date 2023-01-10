package entities;

import controller.UserIO;
import entities.chancecards.ChanceCard;
import entities.chancecards.Deck;
import entities.fields.*;
import controller.View;
import language.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FieldImpl implements FieldAction {
    GameBoard gameBoard;
    View view;
    UserIO userIO;

    public FieldImpl(GameBoard gameBoard, UserIO userIO) {
        this.gameBoard = gameBoard;
        this.userIO = userIO;
    }

    private boolean wantToBuyPrompt(RentableField field) {
        String playerName = gameBoard.getCurrentPlayer().getName();
        String fieldName = field.getName();
        return userIO.promptYesOrNo(Message.buyField(playerName, fieldName));
    }


    @Override
    public Field streetAction(Street street) {

        // If the street is owned by the bank, the player can buy it.
        Field boughtField = null;
        if (street.isNotOwned() && wantToBuyPrompt(street)) {
            boughtField = buyEmptyStreet(street);
        } else {
            streetPayRentToOwner(street);
            userIO.showMessage(Message.payRent(gameBoard.getCurrentPlayer().getName(), street.getName(), String.valueOf(street.getRent(street.getHouses()))));
        }
        return boughtField;
    }

    public void buyProcess() {
        boolean keepBuyingHouse = true;
        while (keepBuyingHouse) {
            if (canBuyHouse()) {

            }
        }

    }

    private boolean canBuyHouse() {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        List<RentableField> currentPlayerOwnedFields = gameBoard.getOwnershipMap().get(currentPlayer);

//        List<RentableField> fieldPairFieldsPlayerOwn = Field.findFieldPairsAllOwnedByPlayer(currentPlayerOwnedFields);


//        if(){
//
//        }
        return false;

    }

    //TODO figure out where this goes

    public void buyHouseProcess() {
        boolean ableToBuyHouse = false;
        List<RentableField> ownedFields = gameBoard.getOwnershipMap().get(gameBoard.getCurrentPlayer());
        List<Street> ownedStreets = new ArrayList<>();
        List<Street> ownedPairStreets = new ArrayList<>();
        for (RentableField ownedField : ownedFields) {
            if (ownedField instanceof Street street && street.getHouses() < 6)
                ownedStreets.add(street);
        }

        for (Street street : ownedStreets) {
            if (streetPlayerOwnsPair(street))
                ableToBuyHouse = true;
            ownedPairStreets.add(street);
        }

        while (ableToBuyHouse && wantToBuyHouse()) {

            HashMap<FieldPair, Integer> minHouses = new HashMap<>();
            for (Street street : ownedPairStreets) {
                minHouses.put(street.getPair(), Math.min(street.getHouses(), minHouses.getOrDefault(street.getPair(), 0)));
            }
            ownedPairStreets = new ArrayList<>();
            for (RentableField ownedField : ownedFields) {
                if (ownedField instanceof Street street && street.getHouses() < 6 && street.getHouses() == minHouses.get(street.getPair()))
                    ownedPairStreets.add(street);
            }

            Message[] choices = new Message[ownedPairStreets.size() + 1];
            for (int i = 0; i < ownedPairStreets.size(); i++) {
                choices[i + 1] = Message.houseOption(ownedPairStreets.get(i).getName(), ownedPairStreets.get(i).getHousePrice() + "");

            }

            choices[0] = Message.finishBuyingHouses();
            Message message = Message.selectHouse();
            int selection = userIO.promptChoice(message, choices);
            if (selection == 0)
                return;
            buyHouse(ownedPairStreets.get(selection - 1));

        }
    }

    private void buyHouse(Street street) {
        street.setHouses(street.getHouses() + 1);
        gameBoard.getCurrentPlayer().addBalance(-street.getHousePrice());
    }

    private boolean wantToBuyHouse() {
        String playerName = gameBoard.getCurrentPlayer().getName();
        return userIO.promptYesOrNo(Message.buyHouse(playerName));
    }

    public RentableField buyEmptyStreet(RentableField street) {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        street.setOwner(currentPlayer);
        currentPlayer.addBalance(-street.getPrice());
        return street;
    }

    @Override
    public void streetPayRentToOwner(Street street) {
        // If the street is owned by another player, the player has to pay rent.
        // We need to find the owner, not just the name, so we can add the rent to him.
        if (street.isNotOwned()) {
            throw new IllegalArgumentException("This method should never be called on a street without a owner.");
        }
        Player[] players = gameBoard.getPlayers();
        Player houseOwner = street.getOwner();
        int rent = street.getRent(street.getHouses());

        for (Player player : players) {
            if (player == houseOwner) {
                houseOwner = player;
            }
        }

//        if (street.getHouses() == 0 && streetPlayerOwnsPair(street)) {
//            rent *= 2;
//        }
        houseOwner.addBalance(rent);
        gameBoard.getCurrentPlayer().addBalance(-rent);
    }

    @Override
    public boolean streetPlayerOwnsPair(Street street) {
        boolean playerOwnsPair = true;
        for (int i : street.getPair().getFieldIds()) {
            Street pairStreet = (Street) gameBoard.getFields()[i];
            if (!(pairStreet.getOwner() == street.getOwner())) {
                playerOwnsPair = false;
            }
        }
        return playerOwnsPair;
    }

    @Override
    public void taxAction(Tax tax) {

        if (tax.getPercentPrice() > 0 && wantToPayPercentPrice(tax)) {
            gameBoard.getCurrentPlayer().addBalance(-tax.getPrice());
            return;
        }
        gameBoard.getCurrentPlayer().addBalance((int) -(gameBoard.totalPlayerValue(gameBoard.getCurrentPlayer()) * 0.1));
    }

    private boolean wantToPayPercentPrice(Tax tax) {
        String fixedPrice = String.valueOf(tax.getPrice());
        String percentPrice = tax.getPercentPrice() + "";
        Message taxQuestion = Message.taxPrompt(fixedPrice, percentPrice);
        return userIO.promptChoice(taxQuestion) == 1;
    }

    @Override
    public void goToJailAction(GoToJail goToJail) {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        userIO.showMessage(Message.goToJail());
        currentPlayer.setPosition(10);
        currentPlayer.setJailed(true);
    }

    @Override
    public void jailAction(Jail jail) {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        currentPlayer.setJailed(true);
        return;
    }


    @Override
    public Field ferryAction(Ferry ferry) {
        Field boughtField = null;
        if (ferry.isNotOwned() && wantToBuyPrompt(ferry)) {
            boughtField = buyEmptyStreet(ferry);
        } else {
            ferryPayRent(ferry);
        }

        return boughtField;
    }

    private void ferryPayRent(Ferry ferry) {

        Player[] players = gameBoard.getPlayers();
        String houseOwnerName = ferry.getOwner().getName();
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
        Deck deck = gameBoard.getDeck();
        ChanceCard chanceCard = deck.takeChanceCard();
        chanceCard.executeCardAction(gameBoard.getActualChanceCard());
        deck.putCardBack(chanceCard);
    }


    @Override
    public Field breweryAction(Brewery brewery) {
        Field boughtField = null;
        if (brewery.isNotOwned() && wantToBuyPrompt(brewery)) {
            boughtField = buyEmptyStreet(brewery);
        } else {
            breweryPayRent(brewery);
        }
        return boughtField;
    }

    public void breweryPayRent(Brewery brewery) {
        int diceSum = gameBoard.getDiceCup().getSum();
        Player[] players = gameBoard.getPlayers();
        String houseOwnerName = brewery.getOwner().getName();
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
