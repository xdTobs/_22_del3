package View;

import Controller.BasicUserIO;
import Controller.View;
import Enities.DiceCup.DiceCup;
import Enities.Fields.Field;
import Enities.Player;
import Language.Message;

public class TestView implements View, BasicUserIO {
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
    public void update(Player[] players, Field[] fields) {

    }

    @Override
    public void updateHouses(Field[] fields) {
        System.out.println("updateHouses");
    }

    @Override
    public int promptChoice(Message message, Message... choices) {
        return 0;
    }

    @Override
    public int promptRange(Message message, int min, int max) {
        return 0;
    }

    @Override
    public void showMessage(Message message) {

    }
}
