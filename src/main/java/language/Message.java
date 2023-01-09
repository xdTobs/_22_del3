package language;


public class Message {

    private final String[] args;
    private Type type;

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


    public Message(Type type, String[] args) {
        this.type = type;
        this.args = args;
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
        return of(Type.ROLL_DICE, playerName, fieldName);
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

    public static Message getOutOfJail(String playerName) {
        return of(Type.GET_OUT_OF_JAIL_CARD, playerName);
    }

    public static Message passedStart(String playerName) {
        return of(Type.PASSED_START, playerName);
    }

    public static Message payRent(String playerName, String fieldName,String rent) {
        return of(Type.PAY_RENT, playerName,fieldName,rent);

    }

    public static Message finishBuyingHouses() {
        return of(Type.STOP_BUYING_HOUSES);
    }


    public static Message parking() {
        return of(Type.PARKING);
    }

    public static Message playerName(int id) {
        switch (id) {
            case (1):
                return of(Type.PLAYER_NAME_1);
            case (2):
                return of(Type.PLAYER_NAME_2);
            case (3):
                return of(Type.PLAYER_NAME_3);
            case (4):
                return of(Type.PLAYER_NAME_4);
            default:
                throw new IllegalArgumentException("The player doesn't exist.\nIllegal ID: " + id);
        }
    }

    public static Message extraTurn(String playerName) {
        return of(Type.EXTRA_TURN, playerName);
    }

    public static Message goToJail() {
        return of(Type.GO_TO_JAIL);
    }
    public static Message houseOption(String streetName,String pricePer){
        return of(Type.HOUSE_OPTION,streetName,pricePer);
    }
    public static Message selectHouse(){
        return of(Type.SELECT_HOUSE);
    }

    public Type getType() {
        return type;
    }

    public String[] getArgs() {
        return args;
    }

    public enum Type {
        HOUSE_OPTION,
        MOVE_TO,
        ON_PICK_FIELD_CARD,
        GAME_WON,
        GAME_LOST,
        ROLL_DICE,
        LEAVE_JAIL,
        CHANCE_CARD,
        PARKING,
        GET_OUT_OF_JAIL_CARD,
        PLAYER_COUNT,
        PLAYER_NAME_4,
        PLAYER_NAME_3,
        PLAYER_NAME_2,
        PLAYER_NAME_1,
        MONEY_GIFT_CARD_SUCCESSFUL,
        MONEY_GIFT_CARD_UNSUCCESSFUL,
        WANT_TO_BUY_FIELD,
        WANT_TO_BAIL_OUT,

        WANT_TO_BUY_HOUSE,
        YES,
        NO,
        EXTRA_TURN,
        TAX_PROMPT,
        PASSED_START,
        STOP_BUYING_HOUSES,
        SELECT_HOUSE,
        PAY_RENT,
        GO_TO_JAIL

    }
}