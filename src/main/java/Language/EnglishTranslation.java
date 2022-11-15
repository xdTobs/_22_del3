package Language;

// TODO Translations for chance cards
public class EnglishTranslation implements Translation {
    private final String[] fieldNames = {"Start", "Burgerbaren", "Pizzariaet", "Chans", "Slikbutikken", "Iskiosken", "Fængsel", "Museet", "Biblioteket", "Chans", "Skaterparken", "Swimmingpoolen", "Parkering", "Spillehallen", "Biografen", "Chans", "Legetøjsbutikken", "Dyrehandlen", "Gå i fængsel", "Bowlinghallen", "Zoo", "Chans", "Vandlandet", "Strandpromenaden"};
    private final String gameWon = "has won the game";
    private final String playerName1 = "Player 1";

    private final String playerName2 = "Player 2";
    private final String onRollDice = "It is your turn. Roll the Dice";
    private final String startTurnJail = "You are in Jail, you lose 1$";
    private final String moveTo = "Move to";
    private final String chanceCard = "pulled a chance card. It tells you to:";

    public String getFieldName(int i ) {
        return fieldNames[i];
    }

    public String gameWonMsg() {
        return " has won the game!";
    }

    public String getPlayerName1() {
        return playerName1;
    }

    public String getPlayerName2() {
        return playerName2;
    }

    public String rollDiceMsg() {
        return onRollDice;
    }


    public String jailMsg() {
        return startTurnJail;
    }

    // TODO implement i in moveToMsg
    public String moveToMsg(int i) {
        return moveTo;
    }

    public String chanceCardMsg() {
        return chanceCard;
    }
}
