package Language;

public interface Translation {
    String getFieldName(int i);

    String gameWonMsg();

    String getPlayerName1();

    String getPlayerName2();

    String rollDiceMsg();

    String gotoJailMsg(String playerName);
    String leaveJailMsg(String playerName);

    // TODO implement i in moveToMsg
    String moveToMsg(int i);

    String moveTo();

    String chanceCardMsg();

    String onPickFieldChance();

    String parkingMsg();
    String startMsg();
    String onGetOutOfJail();
}
