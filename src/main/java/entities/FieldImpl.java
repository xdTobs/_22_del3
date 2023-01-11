package entities;

import controller.UserIO;
import entities.chancecards.ChanceCard;
import entities.chancecards.Deck;
import entities.fields.*;
import language.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FieldImpl implements FieldAction {
    GameBoard gameBoard;
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
            userIO.showMessage(Message.payRent(gameBoard.getCurrentPlayer().getName(), street.getName(), String.valueOf(street.getBaseRent(street.getHouses()))));
        }
        return boughtField;
    }

    //TODO figure out where this goes

    public void buyHouseProcess() {
        boolean ableToBuyHouse = false;
        List<RentableField> ownedFields = gameBoard.getOwnershipMap().get(gameBoard.getCurrentPlayer());
        List<Street> ownedStreets = new ArrayList<>();
        List<Street> ownedPairStreets = new ArrayList<>();
        for (RentableField ownedField : ownedFields) {
            if (ownedField instanceof Street street)
                ownedStreets.add(street);
        }

        for (Street street : ownedStreets) {
            if (streetPlayerOwnsPair(street)) {
                ableToBuyHouse = true;
                ownedPairStreets.add(street);
            }
        }

        while (ableToBuyHouse && wantToBuyHouse()) {

            HashMap<FieldPair, Integer> minHouses = new HashMap<>();
            for (Street street : ownedPairStreets) {
                minHouses.put(street.getPair(), Math.min(street.getHouses(), minHouses.getOrDefault(street.getPair(), street.getHouses())));
            }
            List<Street> availableStreets = new ArrayList<>();
            for (Street ownedPairStreet : ownedPairStreets) {
                if (ownedPairStreet.getHouses() < 5 && ownedPairStreet.getHouses() == minHouses.get(ownedPairStreet.getPair()))
                    availableStreets.add(ownedPairStreet);
            }

            Message[] choices = new Message[availableStreets.size() + 1];
            for (int i = 0; i < availableStreets.size(); i++) {
                choices[i + 1] = Message.houseOption(availableStreets.get(i).getName(), availableStreets.get(i).getHousePrice() + "");

            }

            choices[0] = Message.finishBuyingHouses();
            Message message = Message.selectHouse();
            int selection = userIO.promptChoice(message, choices);
            if (selection == 0 || availableStreets.size() == 0)
                return;
            buyHouse(availableStreets.get(selection - 1));

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
        int rent = street.getBaseRent(street.getHouses());

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

        for (Field field : gameBoard.getFields()) {
            if (field instanceof Jail jail) {
                currentPlayer.setPosition(jail.getPosition());
                currentPlayer.setJailed(true);
                userIO.showMessage(Message.goToJail());
                return;
            }
        }
        throw new IllegalArgumentException("There is no jail, so you can't use goToJail square");
    }

    @Override
    public void jailAction(Jail jail) {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        currentPlayer.setJailed(true);
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
            int rent = ferry.getBaseRent(ferrysOwned);
            houseOwner.addBalance(rent);
            gameBoard.getCurrentPlayer().addBalance(-rent);
        }
    }

    private int ferryPlayerOwns(Ferry ferry) {
        int count = 0;
        for (int i : ferry.getPair().getFieldIds()) {
            Ferry ferryCounter = (Ferry) gameBoard.getFields()[i];
            if (ferryCounter.getOwner() != null && ferryCounter.getOwner().getName().equals(gameBoard.getCurrentPlayer().getName()))
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

    private void breweryPayRent(Brewery brewery) {
        int diceSum = gameBoard.getDiceCup().getSum();
        Player[] players = gameBoard.getPlayers();
        String houseOwnerName = brewery.getOwner().getName();
        Player houseOwner = null;
        for (Player player : players) {
            if (player.getName().equals(houseOwnerName)) {
                houseOwner = player;
            }
        }


        int rent = brewery.getBaseRent(brewery.getHouses());
        if (houseOwner != null) {
            houseOwner.addBalance(rent * diceSum);
            gameBoard.getCurrentPlayer().addBalance(-rent * diceSum);
        }
    }

}
