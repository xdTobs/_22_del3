package Enities;

import ChanceCards.Chance;
import ChanceCards.Deck;
import Controllers.GUI_Controller;
import Enities.Fields.Field;
import Enities.Fields.Street;
import Language.LanguageHandler;
import gui_fields.*;

import java.awt.*;

public class GameBoard {
    final private Field[] fields = new Field[24];

    /**
     * The Cards, chance.
     */
    public Deck cards;

    public GameBoard(Deck cards) {
        this.cards = cards;
        for (int i = 0; i < fields.length; i++) {
            if (i == 0) {
                this.fields[i] = new Field(i, new GUI_Start("Start", "Start", "Start", Color.white, Color.black));
            } else if ((i + 3) % 6 == 0) {
                // ChanceCards.Chance field. 3, 9, 15, 21. Every sixth with an offset of three is chance field.
                this.fields[i] = new Field(i, new GUI_Chance());
            } else if (i == 6) {
                // Jail field
                this.fields[i] = new Field(i, new GUI_Jail());
            } else if (i == 12) {
                // Parking
                this.fields[i] = new Field(i, new GUI_Refuge());
            } else if (i == 18) {
                // Go to jail
                this.fields[i] = new Field(i, new GUI_Jail());
            } else {
//                GUI_Street guiStreet = createStreet(i);
                this.fields[i] = new Street(i);
            }
        }
    }

    public GUI_Field[] getGuiFields() {
        GUI_Field[] guiFields = new GUI_Field[fields.length];
        for (int i = 0; i < fields.length; i++) {
            guiFields[i] = fields[i].getGuiField();
        }
        return guiFields;
    }

    public Field getField(int position) {
        return fields[position];
    }

    public void onStart(GUI_Controller gui_controller) {
        gui_controller.showMessage(LanguageHandler.passedStartMsg());
    }

    public void onRefuge(GUI_Controller gui_controller) {
        gui_controller.showMessage(LanguageHandler.parkingMsg());
    }

    /**
     * We have landed on street.
     *
     * @param street         the street
     * @param currentPlayer  the current player
     * @param gui_controller Controls for the gui
     */
    public void onStreet(GUI_Street street, Player currentPlayer, GUI_Controller gui_controller, Player[] players) {
        //maybe prompt that you landed
        int rent = Integer.parseInt(street.getRent());
        if (street.getOwnerName().equals("Bank")) {
            //currently just shows player id, would be nice to have full names for GUI clicks
            street.setOwnerName(currentPlayer.getName());
            currentPlayer.addBalance(-rent);
        } else {
            // TODO Test if this works. I think it should.
            String houseOwnerName = street.getOwnerName();
            Player houseOwner = null;
            for (Player player : players) {
                if (player.getName().equals(houseOwnerName)) {
                    houseOwner = player;
                }
            }
            assert houseOwner != null;
            // If you land on your own house, you don't have to pay rent. But we can ignore handling that, because paying yourself $2 dollars makes no difference. The gameover check comes much later.
            houseOwner.addBalance(rent);
            currentPlayer.addBalance(-rent);
        }
    }

    /**
     * We have landed on chance, we get a card.
     *
     * @param currentPlayer  the current player
     * @param gui_controller
     */
    // TODO implement action is an abstract method in chance, that is called here.
    public void onChance(Player currentPlayer, GUI_Controller gui_controller) {
        Chance chanceCard = cards.pullCard();
        chanceCard.executeCardAction(currentPlayer);
        gui_controller.displayText(currentPlayer.getName() + " " + LanguageHandler.chanceCardMsg() + chanceCard.getDesc());
    }

    /**
     * We have landed on jail, we need to pay money next turn, unless we have special card.
     *
     * @param currentPlayer  the current player
     * @param gui_controller
     */
    public void onJail(Player currentPlayer, GUI_Controller gui_controller) {
        currentPlayer.setJailed(true);
    }

    // When the player lands on a field, we find out which one here, and then we take action according to field type.
    public void fieldAction(Player currentPlayer, GUI_Controller gui_controller, Player[] players) {
        var playerPosition = currentPlayer.getPos();
        var field = getField(playerPosition);
        GUI_Field guiField = field.getGuiField();
        if (field instanceof Street) {
            GUI_Street guiStreet = (GUI_Street) field.getGuiField();
            onStreet(guiStreet, currentPlayer, gui_controller, players);
        } else if (guiField instanceof GUI_Chance) {
            onChance(currentPlayer, gui_controller);
        } else if (guiField instanceof GUI_Jail) {
            onJail(currentPlayer, gui_controller);
        } else if (guiField instanceof GUI_Start) {
            onStart(gui_controller);
        } else if (guiField instanceof GUI_Refuge) {
            onRefuge(gui_controller);
        }
    }

    public Field[] getFields() {
        return fields;
    }
}
