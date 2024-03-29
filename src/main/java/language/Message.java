package language;


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

    public static Message sell(String playerName) {
        return of(Type.WANT_TO_SELL, playerName);
    }

    public static Message sellHouse() {
        return of(Type.SELL_HOUSE);
    }

    public static Message pawnProperty() {
        return of(Type.PAWN_PROPERTY);
    }

    public static Message dontWantToSell() {
        return of(Type.DONT_WANT_TO_SELL);
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

    public static Message gameOver(String winner, String restOfPlayers) {
        return of(Type.GAME_OVER_MESSAGE, winner, restOfPlayers);
    }

    public static Message chanceCard(String playerName, String description) {
        return of(Type.CHANCE_CARD, playerName, description);
    }

    public static Message youCannotAfford(String playerName) {
        return of(Type.YOU_CANNOT_AFFORD, playerName);
    }

    public static Message leaveJail(String playerName) {
        return of(Type.LEAVE_JAIL, playerName);
    }

    public static Message inJail(String playerName) {
        return of(Type.IN_JAIL, playerName);
    }

    public static Message enterPlayerName(String playerOrder) {
        return of(Type.WHAT_IS_YOUR_NAME, playerOrder);
    }

    public static Message passedStart(String playerName) {
        return of(Type.PASSED_START, playerName);
    }

    public static Message remainingPlayers(String playerNames) {
        return of(Type.REMAINING_PLAYERS, playerNames);
    }

    public static Message payRent(String playerName, String fieldName, String rent) {
        return of(Type.PAY_RENT, playerName, fieldName, rent);
    }

    public static Message noRentInJail(String playerName, String rent, String fieldName, String houseOwner) {
        return of(Type.DONT_PAY_RENT, playerName, rent, fieldName, houseOwner);
    }

    public static Message ThreeSameDieGoToJail(String playerName) {
        return of(Type.THREE_SAME_GO_JAIL, playerName);
    }

    public static Message finishBuyingHouses() {
        return of(Type.STOP_BUYING_HOUSES);
    }

    public static Message pawnSelectProperty() {
        return of(Type.PAWN_SELECT_PROPERTY);
    }

    public static Message sellSelectHouse() {
        return of(Type.SELL_SELECT_HOUSE);
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

    public static Message doYouWantToSell() {
        return of(Type.DO_YOU_WANT_TO_SELL);
    }

    public static Message sellHouseOption(String streetName, String pricePer) {
        return of(Type.SELL_HOUSE_OPTION, streetName, pricePer);
    }

    public static Message buyBackHouseOption(String streetName, String pricePer) {
        return of(Type.BUY_BACK_HOUSE_OPTION, streetName, pricePer);
    }

    public static Message dontWantToBuyBackHouse() {
        return of(Type.DONT_WANT_TO_BUY_BACK_HOUSE);
    }

    public static Message selectHouse() {
        return of(Type.SELECT_HOUSE);
    }

    public static Message buyBack(String name) {
        return of(Type.BUY_BACK, name);
    }

    public static Message selectLanguage() {
        return of(Type.SELECT_LANGUAGE);
    }

    public static Message[] selectLanguage(String[] languages) {
        Message[] messages = new Message[languages.length];
        for (int i = 0; i < languages.length; i++) {
            String capitalized = languages[i].substring(0, 1).toUpperCase() + languages[i].substring(1).toLowerCase();
            messages[i] = of(Type.EMPTY_PLACEHOLDER_FOR_CHOICES, capitalized);
        }
        return messages;
    }

    public Type getType() {
        return type;
    }

    public String[] getArgs() {
        return args;
    }

    public enum Type {
        BUY_BACK,
        BUY_BACK_HOUSE_OPTION,
        CHANCE_CARD,
        DONT_PAY_RENT,
        DONT_WANT_TO_BUY_BACK_HOUSE,
        DONT_WANT_TO_SELL,
        DO_YOU_WANT_TO_SELL,
        EXTRA_TURN,
        GAME_OVER_MESSAGE,
        GO_TO_JAIL_CARD,
        GO_TO_JAIL_FIELD,
        HOUSE_OPTION,
        IN_JAIL,
        LEAVE_JAIL,
        MONEY_GIFT_CARD_SUCCESSFUL,
        MONEY_GIFT_CARD_UNSUCCESSFUL,
        NO,
        PARKING,
        PASSED_START,
        PAWN_PROPERTY,
        PAWN_SELECT_PROPERTY,
        PAY_RENT,
        PLAYER_COUNT,
        REMAINING_PLAYERS,
        ROLL_DICE,
        SELECT_HOUSE,
        SELL_HOUSE,
        SELL_HOUSE_OPTION,
        SELL_SELECT_HOUSE,
        STOP_BUYING_HOUSES,
        TAX_PROMPT,
        THREE_SAME_GO_JAIL,
        WANT_TO_BAIL_OUT, WANT_TO_BUY_FIELD, WANT_TO_BUY_HOUSE, WANT_TO_SELL,
        WHAT_IS_YOUR_NAME,
        YES,
        YOU_CANNOT_AFFORD,
        SELECT_LANGUAGE, EMPTY_PLACEHOLDER_FOR_CHOICES,
    }
}