package Controller;

import Enities.DiceCup.DiceCup;
import Enities.Fields.Field;
import Enities.Player;

public interface View {
    void addPlayersToGui(Player[] players);

    void updateDie(DiceCup diceCup);

    void updatePlayerBalances(Player[] players);

    void updatePlayerLocations(Player[] players);


    void update(Player[] players, Field[] fields);

    void updateHouses(Field[] fields);


}
