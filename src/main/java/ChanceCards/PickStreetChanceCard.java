package ChanceCards;

import Enities.Fields.Street;
import Language.LanguageHandler;
import gui_fields.GUI_Street;
import Enities.*;
import View.*;
import gui_main.GUI;

/**
 * Chance card to pick a street to move to. Also buys / plays rent when landed on.
 */
public class PickStreetChanceCard extends ChanceCard {
    private int[] choices;
    private String[] options;
    private int index;


    public PickStreetChanceCard(int[] choices) {

        this.desc = "temp";
        this.choices = choices;
        options = new String[choices.length];
        for (int i = 0; i < choices.length; i++) {
            options[i] = String.valueOf(choices[i]);
        }

    }

    public void executeCardAction(Player[] players, Player currentPlayer) {



    }
    public void chooseStreet(GUI gui){
        String s = gui.getUserSelection(LanguageHandler.chanceCardMsg() + " " + LanguageHandler.onPickFieldChance(), options);
        for (int i = 0; i < options.length; i++) {
            if (options[i].equals(s)) {
                index = i;

            }
        }


    }

}
