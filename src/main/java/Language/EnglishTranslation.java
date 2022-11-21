package Language;

// TODO Translations for chance cards
public class EnglishTranslation implements Translation {
    private final String[] fieldNames = {"Start", "Burgerbaren", "Pizzeriaet", "Chans", "Slikbutikken", "Iskiosken", "Fængsel", "Museet", "Biblioteket", "Chans", "Skaterparken", "Swimmingpoolen", "Parkering", "Spillehallen", "Biografen", "Chans", "Legetøjsbutikken", "Dyrehandlen", "Gå i fængsel", "Bowlinghallen", "Zoo", "Chans", "Vandlandet", "Strandpromenaden"};

    private final String moveTo = "Move to";
    public final String onPickFieldChance = "Pick a field to move to. If it is available you get it for free. Otherwise you pay rent";

    public String getFieldName(int i) {
        return fieldNames[i];
    }

    public String gameWonMsg(String playerName) {
        return playerName + " has won the game!";
    }

    public String getPlayerName1() {
        return "Player 1";
    }

    public String getPlayerName2() {
        return "Player 2";
    }

    public String rollDiceMsg() {
        return "It is your turn. Roll the Dice";
    }

    @Override
    public String gotoJailMsg(String playerName) {
        return "Go to jail " + playerName;
    }

    public String leaveJailMsg(String playerName) {
        return playerName + " , you are in Jail, you lose 1$";
    }

    // TODO implement i in moveToMsg
    public String moveToMsg(int i) {
        return moveTo;
    }

    public String moveTo() {
        return moveTo;
    }

    public String chanceCardMsg() {
        return "pulled a chance card. It tells you to:";
    }

    public String onPickFieldChance() {
        return onPickFieldChance;
    }

    @Override
    public String parkingMsg() {
        return "You are on parking. There is not much happening here.";
    }

    public String startMsg() {
        return "You get two dollars for landing or passing through start";
    }

    public String onGetOutOfJail() {
        return "Recieve a get out of jail free card. This will automatically be used when you go to jail";
    }
}
