package Language;

// Singleton
public class LanguageHandler {
    private final static Translation translation = new EnglishTranslation();

    public static String getFieldName(int i) {
        return translation.getFieldName(i);
    }

    public static String gameWonMsg() {
        return translation.gameWonMsg();
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

    public static String jailMsg() {
        return translation.jailMsg();
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
    public static String onGetOutOfJail(){
        return translation.onGetOutOfJail();
    }
}
