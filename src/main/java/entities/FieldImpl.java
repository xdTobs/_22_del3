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


    // TODO Vi har sagt til Anton at vi nok vil nå at implementere salg af hus og hotel, og pantsæntning af grunde.
    private void sellHousePawnFieldProcess() {
        throw new RuntimeException("Not implemented yet");
    }

    private boolean wantToBuyPrompt(RentableField field) {
        String playerName = gameBoard.getCurrentPlayer().getName();
        String fieldName = field.getName();
        return userIO.promptBuyField(playerName, fieldName);
    }


    @Override
    public Field streetAction(Street street) {

        // If the street is owned by the bank, the player can buy it.
        Field boughtField = null;
        if (street.isNotOwned()) {
            if (wantToBuyPrompt(street)) {
                boughtField = buyEmptyStreet(street);
            } else {
                return boughtField;
            }
        } else {
            streetPayRentToOwner(street);

            // TODO PRIORITET 1 DOBBELT LEJE ALLE GRUNDE
            // Vi har ikke gjort så man får dobbelt leje hvis man ejer alle grunde i en FieldPair.
            // Denne føles ret basic, og burde vi implementere.
            // TODO PRIORITET 2 INGEN LEJE I FÆNGSEL
            // Reglerne er:
            //  Er man i fængsel, har man stadig ret til at købe grunde (ved
            //  auktion eller handel spillerne imellem), men man kan ikke opkræve leje af de
            //  andre spillere.
            // Begge to burde vi nå. Det skal også skrives test till dem.
            userIO.showMessage(Message.payRent(gameBoard.getCurrentPlayer().getName(), street.getName(), String.valueOf(street.getRent(street.getHouses()))));
        }
        return boughtField;
    }


    // TODO Tobias har du styr på dette?
    // Kan man kun bygge jævnt? Du er nødt til at ha 1 hus på hver grunde i FieldPair, før du kan bygge nummer 2.
    // TODO HOTEL PRIS
    // Hotel skal koste 5 gange prisen af et hus.
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

    // We could generalize this to calculate for all rentableFields.
    // If we do that we need an if statment to check for instanceof Ferry, Brewery and Street,
    // and then call the correct method for calculating rent.
    //
    @Override
    public void streetPayRentToOwner(Street street) {
        /*
        If the street is owned by another player, the player has to pay rent to him.
        First we check two things. If the street is not owned by another player, then we return, because nothing should happen.
        If the street owner is in jail, then the player does not have to pay rent.
         */
        Player streetOwner = street.getOwner();
        if (streetOwner.isJailed() || street.isNotOwned()) {
            return;
        }
        int rent = street.getRent(street.getHouses());

        if (street.getHouses() == 0 && streetPlayerOwnsPair(street)) {
            rent *= 2;
        }
        streetOwner.addBalance(rent);
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
            gameBoard.getCurrentPlayer().addBalance(-tax.getFixedPrice());
            return;
        }
        gameBoard.getCurrentPlayer().addBalance((int) -(gameBoard.totalPlayerValue(gameBoard.getCurrentPlayer()) * 0.1));
    }

    private boolean wantToPayPercentPrice(Tax tax) {
        String fixedPrice = String.valueOf(tax.getFixedPrice());
        String percentPrice = tax.getPercentPrice() + "%";
        Message taxQuestion = Message.taxPrompt(fixedPrice, percentPrice);
        return userIO.promptChoice(taxQuestion) == 0;
    }

    @Override
    public void goToJailAction(GoToJail goToJail) {
        gameBoard.jailPlayer();
        userIO.showMessage(Message.goToJailField());
    }

    @Override
    public void jailAction(Jail jail) {
    }


    @Override
    public Field ferryAction(Ferry ferry) {
        Field boughtField = null;
        if (ferry.isNotOwned() && wantToBuyPrompt(ferry)) {
            boughtField = buyEmptyStreet(ferry);
        } else if (ferry.isNotOwned())
            return null;
        else {
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
            if (houseOwner != gameBoard.getCurrentPlayer()) {
                userIO.showMessage(Message.payRent(gameBoard.getCurrentPlayer().getName(), ferry.getName(), String.valueOf(rent)));
            }
        }

    }

    private int ferryPlayerOwns(Ferry ferry) {
        int count = 0;
        for (int i : ferry.getPair().getFieldIds()) {
            Ferry ferryCounter = (Ferry) gameBoard.getFields()[i];
            if (ferryCounter.getOwner() != null && ferryCounter.getOwner().getName().equals(ferry.getOwner().getName()))
                count++;
        }
        return count - 1;

    }

    @Override
    public void chanceFieldAction(ChanceField chanceField) {
        Deck deck = gameBoard.getDeck();
        ChanceCard chanceCard = deck.pullChanceCard();
        chanceCard.executeCardAction(gameBoard.getChanceCardImpl());
    }


    @Override
    public Field breweryAction(Brewery brewery) {
        Field boughtField = null;
        if (brewery.isNotOwned() && wantToBuyPrompt(brewery)) {
            boughtField = buyEmptyStreet(brewery);
        } else if (brewery.isNotOwned())
            return null;
        else {
            breweryPayRent(brewery);
        }
        return boughtField;
    }

    @Override
    public void parkingAction() {
        userIO.showMessage(Message.parking());
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

        int breweriesOwned = findHowManyInGroupPlayerOwns(brewery);
        int rent = brewery.getRent(breweriesOwned) * diceSum;
        if (houseOwner != null) {
            houseOwner.addBalance(rent);
            gameBoard.getCurrentPlayer().addBalance(-rent);
        }
    }


    private int findHowManyInGroupPlayerOwns(RentableField rentableField) {
        if (rentableField.isNotOwned())
            return 0;
        Player owner = rentableField.getOwner();
        int res = 0;

        FieldPair fp = rentableField.getPair();
        int[] fieldIDs = fp.getFieldIds();
        for (int i : fieldIDs) {
            RentableField field = (RentableField) gameBoard.getFields()[i];
            if (field.isOwned() && field.getOwner() == owner) {
                res++;
            }
        }
        return res;
    }
}
