package View;

import Enities.DiceCup;
import Enities.Fields.Field;
import Enities.Player;

public interface View {
    void addPlayersToGui(Player[] players);

    void updateDie(DiceCup diceCup);

    void updatePlayerBalances(Player[] players);

    void updatePlayerLocations(Player[] players);

    void showMessage(String string);

    void update(DiceCup diceCup, Player[] players, Field[] fields);

    void updateHouses(Field[] fields);

    String promptPlayer(String[] choices, String playerName);

    int promptPlayerCount();
}
