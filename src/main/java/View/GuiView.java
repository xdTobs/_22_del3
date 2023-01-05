package View;

import Enities.DiceCup;
import Enities.Fields.*;
import Enities.Player;
import Language.LanguageHandler;
import gui_fields.*;
import gui_main.GUI;

import java.awt.*;
import java.io.IOException;

public class GuiView implements View {

    LanguageHandler language;

    {
        try {
            language = new LanguageHandler();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final private GUI gui;
    private GUI_Player[] guiPlayers;
    final private GUI_Field[] guiFields = new GUI_Field[40];

    /**
     * Constructor for the GUI_View class.
     * We create GUI_Fields corresponing to the fields in the gameboard.
     * We use the GUI_Fields created to create our GUI.
     *
     * @param fields The fields in the game.
     */
    public GuiView(Field[] fields) {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] instanceof Start start) {
                guiFields[i] = new GUI_Start();
                guiFields[i].setTitle(start.getName());
            }
            if (fields[i] instanceof Street street) {
                guiFields[i] = new GUI_Street();
                guiFields[i].setTitle(street.getName());
                guiFields[i].setBackGroundColor(Color.RED);
                guiFields[i].setSubText(street.getPrice() + "");
            }
            if (fields[i] instanceof ChanceField) {
                guiFields[i] = new GUI_Chance();
            }
            if (fields[i] instanceof Jail jail) {
                guiFields[i] = new GUI_Jail();

            }
            if (fields[i] instanceof Parking parking) {
                guiFields[i] = new GUI_Refuge();

            }
            if (fields[i] instanceof GoToJail goToJail) {
                guiFields[i] = new GUI_Jail();
            }
            if (fields[i] instanceof Ferry ferry) {
                guiFields[i] = new GUI_Shipping();
                guiFields[i].setTitle(ferry.getName());
                guiFields[i].setSubText(ferry.getPrice()+"");
            }
            if (fields[i] instanceof Tax tax) {
                guiFields[i] = new GUI_Tax();
                guiFields[i].setTitle(tax.getName());
            }
            if (fields[i] instanceof Brewery brewery) {
                guiFields[i] = new GUI_Brewery();
                guiFields[i].setTitle(brewery.getName());
                guiFields[i].setSubText(brewery.getPrice()+"");
            }

        }
        this.gui = new GUI(guiFields);
    }


    /**
     * Method to create the players in the GUI.
     *
     * @param players The players in the game
     */
    @Override
    public void addPlayersToGui(Player[] players) {
        // Player colors. Red player 1, blue player 2, green player 3, yellow player 4.
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};

        guiPlayers = new GUI_Player[players.length];

        for (int i = 0; i < players.length; i++) {
            GUI_Car guiCar = new GUI_Car();
            guiCar.setPrimaryColor(colors[i]);
            guiPlayers[i] = new GUI_Player(players[i].getName(), players[i].getBalance(), guiCar);
            gui.addPlayer(guiPlayers[i]);
        }
    }

    @Override
    public void updateDie(DiceCup diceCup) {
        int[] diceValues = diceCup.getArray();
        gui.setDice(diceValues[0], diceValues[1]);
    }

    @Override
    public void updatePlayerBalances(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            GUI_Player guiPlayer = guiPlayers[i];
            guiPlayer.setBalance(players[i].getBalance());
        }
    }

    @Override
    public void updatePlayerLocations(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            GUI_Player guiPlayer = guiPlayers[i];
            guiPlayer.getCar().setPosition(guiFields[players[i].getPosition()]);
        }
    }

    @Override
    public void showMessage(String string) {
        gui.showMessage(string);
    }

    /**
     * Updates all the fields, dices and players in the GUI.
     * The view should be in sync with the model after this method has run.
     *
     * @param diceCup the cup of dices
     * @param players the players in the game
     * @param fields the fields in the game
     */
    @Override
    public void update(DiceCup diceCup, Player[] players, Field[] fields) {
        updatePlayerLocations(players);
        updateHouses(fields);
        updatePlayerBalances(players);
        updateDie(diceCup);
    }

    /**
     * Updates the houses on the GUI.
     * We set the number of houses equal to the player number.
     *
     * @param fields The fields in the game.
     */
    @Override
    public void updateHouses(Field[] fields) {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] instanceof Street street) {
                if (!street.getOwner().equals("Bank")) {
                    // We get the player number from the owner name and sets the number of houses on the field equal to the player number.
                    // player1 => one house.
                    // player2 => two houses.
                    // etc.
                    int value = Integer.parseInt(street.getOwner().replaceAll("[^0-9]", ""));
                    GUI_Street guiStreet = (GUI_Street) guiFields[i];

                    guiStreet.setHouses(value);
                }
            }
        }

    }

    @Override
    public String promptPlayer(String[] choices, String message) {

        return this.gui.getUserSelection(message, choices);
    }

    @Override
    public int promptPlayerCount(int min,int max) {
        //TODO add min max players again
        return this.gui.getUserInteger(language.languageMap.get("playerCountMsg"),min,max);
    }
}
