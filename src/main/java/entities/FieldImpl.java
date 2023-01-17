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

    static long roundToHundred(double num) {
        long i = (long) Math.ceil(num);
        return ((i + 99) / 100) * 100;
    }

    // TODO Vi har sagt til Anton at vi nok vil nå at implementere salg af hus og hotel, og pantsæntning af grunde.
    private void sellHousePawnFieldProcess() {
        throw new RuntimeException("Not implemented yet");
    }

    private boolean wantToBuyPrompt(RentableField field) {
        String playerName = gameBoard.getCurrentPlayer().getName();
        String fieldName = field.getName();
        if(gameBoard.getCurrentPlayer().getBalance()-field.getPrice()>0)
            return userIO.promptBuyField(playerName, fieldName);
        else{
            userIO.showMessage(Message.youCannotAfford(gameBoard.getCurrentPlayer().getName()));
            return false;
        }
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
        } else if(street.getOwner()!=gameBoard.getCurrentPlayer()) {
            streetPayRentToOwner(street);
            userIO.showMessage(Message.payRent(gameBoard.getCurrentPlayer().getName(), street.getName(), String.valueOf(street.getRent(street.getHouses()))));
        }
            // TODO PRIORITET 1 DOBBELT LEJE ALLE GRUNDE
            // Vi har ikke gjort så man får dobbelt leje hvis man ejer alle grunde i en FieldPair.
            // Denne føles ret basic, og burde vi implementere.
            // Reglerne er:
            //  Er man i fængsel, har man stadig ret til at købe grunde (ved
            //  auktion eller handel spillerne imellem), men man kan ikke opkræve leje af de
            //  andre spillere.
            // Begge to burde vi nå. Det skal også skrives test till dem.

        return boughtField;
    }

    public void buyHouseProcess() {
        boolean ableToBuyHouse = false;
        List<RentableField> ownedFields = gameBoard.getOwnershipMap().get(gameBoard.getCurrentPlayer());
        List<Street> ownedStreets = new ArrayList<>();
        List<Street> ownedPairStreets = new ArrayList<>();
        for (RentableField ownedField : ownedFields) {
            if (ownedField instanceof Street street) ownedStreets.add(street);
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
                if (ownedPairStreet.getHouses() < 5 && ownedPairStreet.getHouses() == minHouses.get(ownedPairStreet.getPair())&&gameBoard.getCurrentPlayer().getBalance()-ownedPairStreet.getHousePrice()>0)
                    availableStreets.add(ownedPairStreet);
            }

            Message[] choices = new Message[availableStreets.size() + 1];
            for (int i = 0; i < availableStreets.size(); i++) {
                choices[i + 1] = Message.houseOption(availableStreets.get(i).getName(), availableStreets.get(i).getHousePrice() + "");

            }

            choices[0] = Message.finishBuyingHouses();
            Message message = Message.selectHouse();
            int selection = userIO.promptChoice(message, choices);
            if (selection == 0 || availableStreets.size() == 0) return;
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

    public void sellHousePawnProcess() {
        List<RentableField> ownedFields = gameBoard.getOwnershipMap().get(gameBoard.getCurrentPlayer());
        List<Street> streetsWithHouses = new ArrayList<>();
        List<RentableField> ownedFieldsWithoutHouses = new ArrayList<>();
        for (RentableField ownedField : ownedFields) {
            if (ownedField instanceof Street street && street.getHouses() > 0) streetsWithHouses.add(street);
            else ownedFieldsWithoutHouses.add(ownedField);
        }

        while ((streetsWithHouses.size() > 0 || ownedFieldsWithoutHouses.size() > 0) && !userIO.promptNoOrYes(Message.doYouWantToSell())) {

            ownedFields = gameBoard.getOwnershipMap().get(gameBoard.getCurrentPlayer());
            streetsWithHouses = new ArrayList<>();
            ownedFieldsWithoutHouses = new ArrayList<>();
            for (RentableField ownedField : ownedFields) {
                if (ownedField instanceof Street street && street.getHouses() > 0) streetsWithHouses.add(street);
                else if (!ownedField.isPawned()) ownedFieldsWithoutHouses.add(ownedField);
            }

            Message[] choices;
            int sellHouse = 0;
            if (streetsWithHouses.size() > 0 && ownedFieldsWithoutHouses.size() > 0) {
                choices = new Message[3];
                choices[1] = Message.sellHouse();
                choices[2] = Message.pawnProperty();
                sellHouse = 1;
            } else if (streetsWithHouses.size() > 0) {
                choices = new Message[2];
                choices[1] = Message.sellHouse();
                sellHouse = 1;
            } else {
                choices = new Message[2];
                choices[1] = Message.pawnProperty();
                sellHouse = 2;
            }
            choices[0] = Message.dontWantToSell();
            int choice = userIO.promptChoice(Message.doYouWantToSell(), choices);

            if (choice == 0) return;
            if (choice == 2 || sellHouse == 2 && ownedFieldsWithoutHouses.size() > 0)
                pawnPropertyProcess(ownedFieldsWithoutHouses);
            else if (choice == 1 && sellHouse == 1) {
                sellPropertyProcess(streetsWithHouses);
            }
        }

    }

    private void sellPropertyProcess(List<Street> streetsWithHouses) {
        List<Street> streetsWithMaxHouses = new ArrayList<>();
        HashMap<FieldPair, Integer> maxHouses = new HashMap<>();
        for (Street street : streetsWithHouses) {
            maxHouses.put(street.getPair(), Math.max(street.getHouses(), maxHouses.getOrDefault(street.getPair(), street.getHouses())));
        }
        for (Street s : streetsWithHouses) {
            if (s.getHouses() == maxHouses.get(s.getPair())) streetsWithMaxHouses.add(s);
        }
        Message[] choices = new Message[streetsWithMaxHouses.size() + 1];
        for (int i = 0; i < streetsWithMaxHouses.size(); i++) {
            choices[i + 1] = Message.sellHouseOption(streetsWithMaxHouses.get(i).getName(), streetsWithMaxHouses.get(i).getHousePrice() / 2 + "");
        }

        choices[0] = Message.dontWantToSell();
        int choice = userIO.promptChoice(Message.sellSelectHouse(), choices);
        if (choice == 0) return;
        int houses = streetsWithMaxHouses.get(choice - 1).getHouses();
        if (houses == 0) throw new RuntimeException("shouldnt be called on property with no houses");
        streetsWithMaxHouses.get(choice - 1).setHouses(houses - 1);
        gameBoard.getCurrentPlayer().addBalance(streetsWithMaxHouses.get(choice - 1).getHousePrice() / 2);
    }

    private void pawnPropertyProcess(List<RentableField> streetsWithoutHouses) {

        Message[] choices = new Message[streetsWithoutHouses.size() + 1];
        for (int i = 0; i < streetsWithoutHouses.size(); i++) {
            choices[i + 1] = Message.sellHouseOption(streetsWithoutHouses.get(i).getName(), streetsWithoutHouses.get(i).getPrice() / 2 + "");

        }
        choices[0] = Message.dontWantToSell();

        int choice = userIO.promptChoice(Message.pawnSelectProperty(), choices);
        if (choice == 0) return;
        streetsWithoutHouses.get(choice - 1).setPawned(true);
        gameBoard.getCurrentPlayer().addBalance(streetsWithoutHouses.get(choice - 1).getPrice() / 2);
    }

    private int wantToSell(Message... choices) {

        String playerName = gameBoard.getCurrentPlayer().getName();
        return userIO.promptChoice(Message.sell(playerName), choices);
    }

    public void buyPawnedFields() {
        List<RentableField> ownedFields = gameBoard.getOwnershipMap().get(gameBoard.getCurrentPlayer());
        List<RentableField> pawnedFields = new ArrayList<>();

        for (RentableField ownedField : ownedFields) {
            int price = ownedField.getPrice();
            int half = price / 2;
            double repay = Math.ceil(half + half * 0.1);
            long round = roundToHundred(repay);

            if (ownedField.isPawned() && gameBoard.getCurrentPlayer().getBalance() - round > 0)
                pawnedFields.add(ownedField);
        }
        if (pawnedFields.size() == 0) return;
        while (pawnedFields.size() > 0 && wantToBuyBack()) {
            pawnedFields = new ArrayList<>();
            for (RentableField ownedField : ownedFields) {
                int price = ownedField.getPrice();
                int half = price / 2;
                double repay = Math.ceil(half + half * 0.1);
                long round = roundToHundred(repay);

                if (ownedField.isPawned() && gameBoard.getCurrentPlayer().getBalance() - round > 0)
                    pawnedFields.add(ownedField);
            }
            if (pawnedFields.size() == 0) return;


            Message[] choices = new Message[pawnedFields.size() + 1];
            long[] round = new long[pawnedFields.size()];
            for (int i = 0; i < pawnedFields.size(); i++) {
                int price = pawnedFields.get(i).getPrice();
                int half = price / 2;
                double repay = Math.ceil(half + half * 0.1);
                round[i] = roundToHundred(repay);
                choices[i + 1] = Message.buyBackHouseOption(pawnedFields.get(i).getName(), round[i] + "");
            }

            choices[0] = Message.dontWantToBuyBackHouse();
            int choice = userIO.promptChoice(Message.sellSelectHouse(), choices);
            if (choice == 0) return;
            pawnedFields.get(choice - 1).setPawned(false);
            gameBoard.getCurrentPlayer().addBalance(Math.toIntExact(-round[choice - 1]));
        }
    }

    private boolean wantToBuyBack() {
        return userIO.promptYesOrNo(Message.buyBack(gameBoard.getCurrentPlayer().getName()));
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
        if (street.isNotOwned()) {
            return;
        }
        Player houseOwner = street.getOwner();
        int rent = street.getRent(street.getHouses());

        if (street.getHouses() == 0 && streetPlayerOwnsPair(street)) {
            rent *= 2;
        }
        if (!houseOwner.isJailed()){

            houseOwner.addBalance(rent);
            gameBoard.getCurrentPlayer().addBalance(-rent);
        }else{
            userIO.showMessage(Message.noRentInJail(gameBoard.getCurrentPlayer().getName(),String.valueOf(street.getRent(street.getHouses())), street.getName(), street.getOwner().getName()));
        }

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
        } else if (ferry.isNotOwned()) return null;
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
        if (houseOwner != null){
            if (!houseOwner.isJailed()){
                int ferrysOwned = ferryPlayerOwns(ferry);
                int rent = ferry.getRent(ferrysOwned);
                houseOwner.addBalance(rent);
                gameBoard.getCurrentPlayer().addBalance(-rent);
                if (houseOwner != gameBoard.getCurrentPlayer()) {
                    userIO.showMessage(Message.payRent(gameBoard.getCurrentPlayer().getName(), ferry.getName(), String.valueOf(rent)));
                }
            }else {
                userIO.showMessage(Message.noRentInJail(gameBoard.getCurrentPlayer().getName(),String.valueOf(ferry.getRent(ferryPlayerOwns(ferry))), ferry.getName(), ferry.getOwner().getName()));
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
        } else if (brewery.isNotOwned()) return null;
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
        if (houseOwner != null){
            if (!houseOwner.isJailed()){
                int breweriesOwned = findHowManyInGroupPlayerOwns(brewery);
                int rent = (brewery.getRent(breweriesOwned) * diceSum)/2;
                houseOwner.addBalance(rent);
                gameBoard.getCurrentPlayer().addBalance(-rent);
                if (houseOwner != gameBoard.getCurrentPlayer()) {
                    userIO.showMessage(Message.payRent(gameBoard.getCurrentPlayer().getName(), brewery.getName(), String.valueOf((brewery.getRent(findHowManyInGroupPlayerOwns(brewery)) * diceSum)/2)));
                }
            }else {
                userIO.showMessage(Message.noRentInJail(gameBoard.getCurrentPlayer().getName(),String.valueOf((brewery.getRent(findHowManyInGroupPlayerOwns(brewery)) * diceSum)/2), brewery.getName(), brewery.getOwner().getName()));
            }
        }
    }




    private int findHowManyInGroupPlayerOwns(RentableField rentableField) {
        if (!rentableField.isOwned()) return 0;
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
