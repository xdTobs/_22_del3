package ChanceCards;

import Enities.Fields.Field;
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
    private Street street;


    public PickStreetChanceCard(int[] choices) {


        this.desc = "temp";
        this.choices = choices;


    }

    public void executeCardAction(Player[] players, Player currentPlayer) {


        currentPlayer.setPosition(street.getPosition());
        if (street.getOwner().equals("Bank")) {
            currentPlayer.addBalance(-street.getRent());
            street.setOwner(currentPlayer.getName());
        } else {
//            gameHandler.gameBoard.onStreet(street, currentPlayer, gui_controller, gameHandler.players);
//            street.getOwner().addBalance(street.getRent());
            Player owner = null;
            for (Player p : players) {
                if (p.getName().equals(street.getOwner())) {
                    owner = p;
                }
            }
            owner.addBalance(street.getRent());
            currentPlayer.addBalance(-street.getRent());
        }
    }




    public int chooseStreet(GUI gui, Player p, Field[] fields){
        options = new String[choices.length];
        for (int i = 0; i < choices.length; i++) {
            options[i] = (fields[choices[i]]).getName();
        }
        String s = gui.getUserSelection(p.getName()+" "+LanguageHandler.chanceCardMsg() + " " + LanguageHandler.onPickFieldChance(), options);
        for (int i = 0; i < options.length; i++) {
            if (fields[choices[i]].getName().equals(s)) {
                return choices[i];

            }
        }

return -1;
    }

    public void setStreet(Street street) {
        this.street = street;
    }
}
