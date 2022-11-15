package ChanceCards;

import Language.LanguageHandler;
import gui_fields.GUI_Street;
import Enities.*;
import Controllers.*;

public class PickStreetChance extends Chance {
    private GUI_Street[] choices;
    private String[] options;
    private GameHandler game;


    public PickStreetChance(String desc, GUI_Controller gui_controller, GUI_Street[] choices, GameHandler game) {
        this.gui_controller = gui_controller;
        this.desc = desc;
        this.choices = choices;
        this.game = game;
        options = new String[choices.length];
        for (int i = 0; i < choices.length; i++) {
            options[i] = choices[i].getTitle();
        }

    }

    public void pullCard(Player p) {

        String s = gui_controller.getGui().getUserSelection(LanguageHandler.chanceCardMsg() + " " + LanguageHandler.onPickFieldChance(), options);
        for (int i = 0; i < options.length; i++) {
            if (options[i].equals(s)) {
                MoveGetChance temp = new MoveGetChance(gui_controller, i, game);
                temp.pullCard(p);
                break;
            }
        }
        System.out.println(s);

    }

}
