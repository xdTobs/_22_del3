package view;

import controller.BasicUserIO;
import controller.View;
import entities.dicecup.DiceCup;
import entities.fields.Field;
import entities.Player;
import language.Message;

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
    public void movePlayerVisually(Player currentPlayer, DiceCup diceCup) {

    }


    @Override
    public void update(Player[] players, Field[] fields, DiceCup diceCup) {

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
    public String promptString(Message message) {
        return null;
    }

    @Override
    public void showMessage(Message message) {

    }
}
