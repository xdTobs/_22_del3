package Language;

public interface Translation {
    String getFieldName(int i);
    String gameWonMsg();
    String getPlayerName1();
    String getPlayerName2();
    String rollDiceMsg();
    String jailMsg();
    // TODO implement i in moveToMsg
    String moveToMsg(int i);
    String chanceCardMsg();
}
