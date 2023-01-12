package language;


import entities.Player;

import java.util.Arrays;

public class Message {

    private final String[] args;
    private final Type type;

    public Message(Type type, String[] args) {
        this.type = type;
        this.args = args;
    }

    public static Message of(Type type, String... args) {
        return new Message(type, args);
    }

    // All the methods that return of(...) is just for making the code easier to read in the app.
    public static Message buyField(String playerName, String fieldName) {
        return of(Type.WANT_TO_BUY_FIELD, playerName, fieldName);
    }

    public static Message buyHouse(String playerName) {
        return of(Type.WANT_TO_BUY_HOUSE, playerName);
    }

    public static Message bailOut(String playerName) {
        return of(Type.WANT_TO_BAIL_OUT, playerName);
    }

    public static Message taxPrompt(String setPrice, String percentage) {
        return of(Type.TAX_PROMPT, setPrice, percentage);
    }

    public static Message numberOfPlayers() {
        return of(Type.PLAYER_COUNT);
    }

    public static Message yes() {
        return of(Type.YES);
    }

    public static Message no() {
        return of(Type.NO);
    }

    public static Message giftToPoorPlayerCard(String playerName, int amount) {
        return of(Type.MONEY_GIFT_CARD_SUCCESSFUL, playerName, String.valueOf(amount));
    }

    public static Message noGiftToRichPlayerCard(String playerName, int amount) {
        return of(Type.MONEY_GIFT_CARD_UNSUCCESSFUL, playerName, String.valueOf(amount));
    }

    public static Message rollDice(String playerName) {
        return of(Type.ROLL_DICE, playerName);
    }

    public static Message moveTo(String playerName, String fieldName) {
        return of(Type.MOVE_TO, playerName, fieldName);
    }

    public static Message pickField() {
        return of(Type.ON_PICK_FIELD_CARD);
    }

    public static Message gameWon(String playerName) {
        return of(Type.GAME_WON, playerName);
    }

    public static Message gameLost(String playerName) {
        return of(Type.GAME_LOST, playerName);
    }

    public static Message chanceCard(String playerName, String description) {
        return of(Type.CHANCE_CARD, playerName, description);
    }

    public static Message leaveJail(String playerName) {
        return of(Type.LEAVE_JAIL, playerName);
    }

    public static Message enterPlayerName(String playerOrder) {
        return of(Type.WHAT_IS_YOUR_NAME, playerOrder);
    }

    public static Message getOutOfJail(String playerName) {
        return of(Type.GET_OUT_OF_JAIL_CARD, playerName);
    }

    public static Message passedStart(String playerName) {
        return of(Type.PASSED_START, playerName);
    }

    public static Message remainingPlayers(Player[] players) {
        String playerNames = Arrays.stream(players).map(Player::getName).reduce("", (acc, name) -> acc + ", " + name);
        return of(Type.REMAINING_PLAYERS, playerNames);
    }

    public static Message payRent(String playerName, String fieldName, String rent) {
        return of(Type.PAY_RENT, playerName, fieldName, rent);

    }

    public static Message finishBuyingHouses() {
        return of(Type.STOP_BUYING_HOUSES);
    }


    public static Message parking() {
        return of(Type.PARKING);
    }

    public static Message extraTurn(String playerName) {
        return of(Type.EXTRA_TURN, playerName);
    }

    public static Message goToJailField() {
        return of(Type.GO_TO_JAIL_FIELD);
    }

    public static Message goToJailCard() {
        return of(Type.GO_TO_JAIL_CARD);
    }

    public static Message houseOption(String streetName, String pricePer) {
        return of(Type.HOUSE_OPTION, streetName, pricePer);
    }

    public static Message selectHouse() {
        return of(Type.SELECT_HOUSE);
    }

    public Type getType() {
        return type;
    }

    public String[] getArgs() {
        return args;
    }

    public enum Type {
        CHANCE_CARD,
        EXTRA_TURN,
        GAME_LOST,
        GAME_WON,
        GET_OUT_OF_JAIL_CARD,
        GO_TO_JAIL_CARD,
        GO_TO_JAIL_FIELD,
        HOUSE_OPTION,
        LEAVE_JAIL,
        MONEY_GIFT_CARD_SUCCESSFUL,
        MONEY_GIFT_CARD_UNSUCCESSFUL,
        MOVE_TO,
        NO,
        ON_PICK_FIELD_CARD,
        PARKING,
        PASSED_START,
        PAY_RENT,
        PLAYER_COUNT,

        REMAINING_PLAYERS,
        ROLL_DICE,
        SELECT_HOUSE,
        STOP_BUYING_HOUSES,
        TAX_PROMPT,
        WANT_TO_BAIL_OUT,
        WANT_TO_BUY_FIELD,
        WANT_TO_BUY_HOUSE,
        WHAT_IS_YOUR_NAME,
        YES,
    }
}