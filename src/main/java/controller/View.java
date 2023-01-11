package controller;

import entities.dicecup.DiceCup;
import entities.fields.Field;
import entities.Player;

public interface View {
    void addPlayersToGui(Player[] players);

    void updateDie(DiceCup diceCup);

    void updatePlayerBalances(Player[] players);

    void updatePlayerLocations(Player[] players);
    void movePlayerVisually(Player currentPlayer, DiceCup diceCup);

    void update(Player[] players, Field[] fields, DiceCup diceCup);

    void updateHouses(Field[] fields);


}
