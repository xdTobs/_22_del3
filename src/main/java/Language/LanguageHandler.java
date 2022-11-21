package Language;

// We can use this class to implement different languages. For example, we can make translation variable be an array and then we can loop through it and print out the different translations, so the game is multi-lingual.
public class LanguageHandler {
    private final static Translation translation = new EnglishTranslation();

    public static String getFieldName(int i) {
        return translation.getFieldName(i);
    }

    public static String gameWonMsg() {
        return translation.gameWonMsg();
    }

    public static String gameLostMsg() {
        return translation.gameLostMsg();
    }


    public static String getPlayerName1() {
        return translation.getPlayerName1();
    }

    public static String getPlayerName2() {
        return translation.getPlayerName2();
    }

    public static String rollDiceMsg() {
        return translation.rollDiceMsg();
    }

    public static String jailMsg(String playerName) {
        return translation.leaveJailMsg(playerName);
    }

    public static String moveToMsg(int i) {
        return translation.moveToMsg(i);
    }

    public static String moveTo() {
        return translation.moveTo();
    }

    public static String chanceCardMsg() {
        return translation.chanceCardMsg();
    }

    public static String onPickFieldChance() {
        return translation.onPickFieldChance();
    }

    public static String parkingMsg() {
        return translation.parkingMsg();
    }

    public static String passedStartMsg() {
        return translation.startMsg();
    }

    public static String onGetOutOfJail() {
        return translation.onGetOutOfJail();
    }
    public static String promptPlayerCount(){
        return translation.promptPlayerCount();
    }
}
