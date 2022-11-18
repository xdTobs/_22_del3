package ChanceCards;

import Language.LanguageHandler;
import gui_fields.GUI_Street;
import Enities.*;
import View.*;

public class PickStreetChanceCard extends ChanceCard {
    private GUI_Street[] choices;
    private String[] options;


    public PickStreetChanceCard(String desc, GUI_Controller gui_controller, GUI_Street[] choices) {
        this.gui_controller = gui_controller;
        this.desc = desc;
        this.choices = choices;
        options = new String[choices.length];
        for (int i = 0; i < choices.length; i++) {
            options[i] = choices[i].getTitle();
        }

    }

    public void executeCardAction(Player[] players, Player currentPlayer) {

        String s = gui_controller.getGui().getUserSelection(LanguageHandler.chanceCardMsg() + " " + LanguageHandler.onPickFieldChance(), options);
        for (int i = 0; i < options.length; i++) {
            if (options[i].equals(s)) {
//                MoveGetChanceCard temp = new MoveGetChanceCard(players, currentPlayer);
//                temp.executeCardAction(players, currentPlayer);
                break;
            }
        }
        System.out.println(s);

    }

}
