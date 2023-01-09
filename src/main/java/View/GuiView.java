package View;

import Controller.BasicUserIO;
import Controller.View;
import Enities.DiceCup.DiceCup;
import Enities.Fields.*;
import Enities.Player;
import Language.LanguageController;
import Language.Message;
import gui_fields.*;
import gui_main.GUI;

import java.awt.*;
import java.util.Arrays;

public class GuiView implements View, BasicUserIO {

    final private GUI gui;
    private GUI_Player[] guiPlayers;
    private GUI_Field[] guiFields = new GUI_Field[40];
    private LanguageController languageController;

    /**
     * Constructor for the GUI_View class.
     * We create GUI_Fields corresponding to the fields in the gameboard.
     * We use the GUI_Fields created to create our GUI.
     */
    public GuiView(GUI_Field[] guiFields, LanguageController languageController) {
        this.languageController = languageController;
        this.gui = new GUI(guiFields);
        this.guiFields = gui.getFields();
    }

    /**
     * If this gets called with no choices, then we will instead use the args as choices.
     * We do this so we can use buy houses thing.
     */
    @Override
    public int promptChoice(Message message, Message... choices) {
        String[] choiceArray = new String[0];
        if (choices.length != 0) {
            choiceArray = new String[choices.length];
            for (int i = 0; i < choices.length; i++) {
                choiceArray[i] = languageController.getMessage(choices[i]);
            }
        } else if (message.getArgs().length > 1) {
            choiceArray = message.getArgs();
        }
        return promptChoice(message, choiceArray);
    }

    /**
     * We have this method only for the SELECT_HOUSE Message.Type.
     * We need to be able to construct a choice prompt with field names as values,
     * without having to fields ot the Message.Type enum.
     */
    private int promptChoice(Message message, String[] choicesArray) {
        String answer = this.gui.getUserSelection(languageController.getMessage(message), choicesArray);
        for (int i = 0; i < choicesArray.length; i++) {
            if (answer.equals(choicesArray[i])) {
                return i;
            }
        }
        String choices = Arrays.stream(choicesArray).reduce("", (acc, choice) -> acc + ", " + choice);
        throw new UnsupportedOperationException("An answer have been given that is not in the choices array.\nAnswer given: " + answer + "\nChoices: " + choices);
    }

    @Override
    public int promptRange(Message message, int min, int max) {
        return this.gui.getUserInteger(languageController.getMessage(message), min, max);
    }

    @Override
    public void showMessage(Message message) {
        gui.showMessage(languageController.getMessage(message));
    }


    public static GuiView setup(Field[] fields) {
        GUI_Field[] guiFields = new GUI_Field[40];
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] instanceof Start start) {
                guiFields[i] = createGuiField(new GUI_Start(), start);
            }
            if (fields[i] instanceof Street street) {
                guiFields[i] = createGuiField(new GUI_Street(), street, street.getPrice() + "");
            }
            if (fields[i] instanceof ChanceField chanceField) {
                guiFields[i] = createGuiField(new GUI_Chance(), chanceField);
            }
            if (fields[i] instanceof Jail jail) {
                guiFields[i] = createGuiField(new GUI_Jail(), jail);
            }
            if (fields[i] instanceof Parking parking) {
                guiFields[i] = createGuiField(new GUI_Refuge(), parking);
            }
            if (fields[i] instanceof GoToJail goToJail) {
                guiFields[i] = createGuiField(new GUI_Jail(), goToJail);
            }
            if (fields[i] instanceof Ferry ferry) {
                guiFields[i] = createGuiField(new GUI_Shipping(), ferry, ferry.getPrice() + "");
            }
            if (fields[i] instanceof Tax tax) {
                guiFields[i] = createGuiField(new GUI_Tax(), tax);
            }
            if (fields[i] instanceof Brewery brewery) {
                guiFields[i] = createGuiField(new GUI_Brewery(), brewery, brewery.getPrice() + "");
            }
        }
        return new GuiView(guiFields, new LanguageController());
    }

    private static GUI_Field createGuiField(GUI_Field gf, Field field, String subtext) {
        gf.setTitle(field.getName());
        gf.setBackGroundColor(field.getPair().getBackgroundColor());
        gf.setForeGroundColor(field.getPair().getForegroundColor());
        gf.setSubText(subtext);
        return gf;
    }

    private static GUI_Field createGuiField(GUI_Field gf, Field field) {
        return createGuiField(gf, field, "");
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
        int[] diceValues = diceCup.getDiceValues();
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

    /**
     * Updates all the fields, dices and players in the GUI.
     * The view should be in sync with the model after this method has run.
     *
     * @param players the players in the game
     * @param fields  the fields in the game
     */
    @Override
    public void update(Player[] players, Field[] fields) {
        updatePlayerLocations(players);
        updateHouses(fields);
        updatePlayerBalances(players);

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


}
