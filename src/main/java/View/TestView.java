package View;

import Enities.DiceCup;
import Enities.Fields.Field;
import Enities.Player;

public class TestView implements View {
    @Override
    public void addPlayersToGui(Player[] players) {

    }

    @Override
    public void updateDie(DiceCup diceCup) {

    }

    @Override
    public void updatePlayerBalances(Player[] players) {

    }

    @Override
    public void updatePlayerLocations(Player[] players) {

    }

    @Override
    public void showMessage(String string) {
    }

    @Override
    public void update(DiceCup diceCup, Player[] players, Field[] fields) {

    }

    @Override
    public void updateHouses(Field[] fields) {

    }

    @Override
    public String promptPlayer(String[] choices, String playerName) {
        return null;
    }

    @Override
    public int promptPlayerCount() {
        return 0;
    }
}
