package Language;
// Singleton
public class LanguageHandler {
    private static Translation translation;
    private static LanguageHandler instance;
    private LanguageHandler() {
        translation = new EnglishTranslation();
    }
    public static LanguageHandler getInstance() {
        if (LanguageHandler.instance == null) {
            LanguageHandler.instance = new LanguageHandler();
        }
        return LanguageHandler.instance;
    }

    public static String getFieldName(int i) {
        return getInstance().translation.getFieldName(i);
    }

    public static String gameWonMsg() {
        return getInstance().translation.gameWonMsg();
    }

    public static String getPlayerName1() {
        return getInstance().translation.getPlayerName1();
    }

    public static String getPlayerName2() {
        return getInstance().translation.getPlayerName2();
    }

    public static String rollDiceMsg() {
        return getInstance().translation.rollDiceMsg();
    }

    public static String jailMsg() {
        return getInstance().translation.jailMsg();
    }

    public static String moveToMsg(int i) {
        return getInstance().translation.moveToMsg(i);
    }

    public static String moveTo() {
        return getInstance().translation.moveTo();
    }

    public static String chanceCardMsg() {
        return getInstance().translation.chanceCardMsg();
    }
    public static String onPickFieldChance() {
        return getInstance().translation.onPickFieldChance();
    }
}
