package view;

import controller.BasicUserIO;
import controller.View;
import entities.Player;
import entities.dicecup.DiceCup;
import entities.fields.*;
import gui_fields.*;
import gui_main.GUI;
import language.LanguageHandler;
import language.Message;

import java.awt.*;
import java.util.Arrays;

public class GuiView implements View, BasicUserIO {

    final private GUI gui;
    private GUI_Player[] guiPlayers;
    private LanguageHandler languageHandler;

    /**
     * Constructor for the GUI_View class.
     * We create GUI_Fields corresponding to the fields in the gameboard.
     * We use the GUI_Fields created to create our GUI.
     */
    public GuiView(GUI gui, LanguageHandler languageHandler) {
        this.gui = gui;
        this.languageHandler = languageHandler;
    }

    public static GuiView setup(Field[] fields, LanguageHandler languageHandler) {
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

        GuiView guiview = new GuiView(new GUI(guiFields), languageHandler);
        guiview.updateHousesAndFields(fields);
        return guiview;
    }

    /**
     * If this gets called with no choices, then we will instead use the args as choices.
     * We do this, so we can use buy houses thing.
     */
    @Override
    public int promptChoice(Message message, Message... choices) {
        String[] choiceArray = new String[0];
        if (choices.length != 0) {
            choiceArray = new String[choices.length];
            for (int i = 0; i < choices.length; i++) {
                choiceArray[i] = languageHandler.getMessage(choices[i]);
            }
        } else if (message.getArgs().length > 1) {
            choiceArray = message.getArgs();
        }
        return promptChoice(message, choiceArray);
    }

    /**
     * We have this method only for the SELECT_HOUSE Message.Type.
     * We need to be able to construct a choice prompt with field names as values,
     * without having fields at the Message.Type enum.
     */
    private int promptChoice(Message message, String[] choicesArray) {
        String answer = this.gui.getUserSelection(languageHandler.getMessage(message), choicesArray);
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
        return this.gui.getUserInteger(languageHandler.getMessage(message), min, max);
    }

    @Override
    public String promptString(Message message) {
        return this.gui.getUserString(languageHandler.getMessage(message));
    }

    @Override
    public void showMessage(Message message) {
        gui.showMessage(languageHandler.getMessage(message));
    }

    @Override
    public void setLanguageHandler(LanguageHandler languageHandler) {
        this.languageHandler = languageHandler;
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
    public void updateDie(DiceCup randomDiceCup) {
        int[] diceValues = randomDiceCup.getDiceValues();
        gui.setDice(diceValues[0], diceValues[1]);
    }

    @Override
    public void updatePlayerBalances(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            GUI_Player guiPlayer = guiPlayers[i];
            guiPlayer.setBalance(players[i].getBalance());
        }
    }

    /**
     * Only move player when moving with diceroll. Still teleport if moving with chanceCard or gotojail.
     */
    @Override
    public void movePlayerVisually(Player currentPlayer, DiceCup randomDiceCup) {
        int startPos = currentPlayer.getPosition();
        int sum = randomDiceCup.getSum();
        GUI_Player guiPlayer = findGuiPlayerFromPlayer(currentPlayer);
        for (int i = 1; i <= sum; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (startPos + i >= 40) {
                setGuiPosition(guiPlayer, (startPos + i) % 40);
            } else {
                setGuiPosition(guiPlayer, startPos + i);
            }
            /*if (startPos + i < 40) {
                setGuiPosition(guiPlayer, startPos + i);
            } else {
                setGuiPosition(guiPlayer, 40 - startPos + i);
            }*/
        }
    }

    // This throws unchecked exception if no player is found.
    private GUI_Player findGuiPlayerFromPlayer(Player player) {
        return Arrays.stream(guiPlayers).filter(guiPlayer -> player.getName().equals(guiPlayer.getName())).findFirst().orElseThrow();
    }

    private void setGuiPosition(GUI_Player guiPlayer, int position) {
        guiPlayer.getCar().setPosition(gui.getFields()[position]);
    }

    @Override
    public void updatePlayerLocations(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            GUI_Player guiPlayer = guiPlayers[i];
            guiPlayer.getCar().setPosition(gui.getFields()[players[i].getPosition()]);
        }
    }

    /**
     * Updates all the fields, dices and players in the GUI.
     * The view should be in sync with the model after this method has run.
     */
    @Override
    public void update(Player[] players, Field[] fields, DiceCup randomDiceCup) {
        updatePlayerLocations(players);
        updateHousesAndFields(fields);
        updatePlayerBalances(players);
        updateDie(randomDiceCup);
    }

    /**
     * Updates the houses on the GUI.
     * We set the number of houses equal to the player number.
     *
     * @param fields The fields in the game.
     */
    @Override
    public void updateHousesAndFields(Field[] fields) {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] instanceof RentableField rentableField) {
                if (rentableField.isOwned()) {
                    GUI_Ownable guiOwnable = (GUI_Ownable) gui.getFields()[i];
                    Player owner = rentableField.getOwner();
                    GUI_Player guiOwner = Arrays.stream(guiPlayers).filter(guiPlayer -> owner.getName().equals(guiPlayer.getName())).findFirst().get();

                    guiOwnable.setBorder(guiOwner.getPrimaryColor());

                } else {
                    GUI_Ownable guiOwnable = (GUI_Ownable) gui.getFields()[i];
                    guiOwnable.setBorder(Color.BLACK);
                }
            }
            if (gui.getFields()[i] instanceof GUI_Street gui_street && fields[i] instanceof Street street) {
                if (street.getHouses() < 5) {
                    int houses = street.getHouses();
                    gui_street.setHotel(false);
                    gui_street.setHouses(houses);

                } else if (street.getHouses() == 5) {
                    gui_street.setHouses(0);
                    gui_street.setHotel(true);
                } else {
                    throw new RuntimeException("invalid amount of houses");
                }

            }

            gui.getFields()[i].setDescription(formatDesc(fields[i]));
        }
    }

    public String formatDesc(Field field) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><center>");
        sb.append(field.getName());
        sb.append("<br>");
        if (field instanceof RentableField rf) {
            sb.append("Price: ").append(rf.getPrice());
            sb.append("<br>");
            if (rf instanceof Street street) {
                sb.append("House Price: ").append(street.getHousePrice()).append("<br>");
                sb.append("Rent with 0 houses: ").append(rf.getRent(0)).append("<br>");
                sb.append("Rent with 1 houses: ").append(rf.getRent(1)).append("<br>");
                sb.append("Rent with 2 houses: ").append(rf.getRent(2)).append("<br>");
                sb.append("Rent with 3 houses: ").append(rf.getRent(3)).append("<br>");
                sb.append("Rent with 4 houses: ").append(rf.getRent(4)).append("<br>");
                sb.append("Rent with hotel: ").append(rf.getRent(5)).append("<br>");
            }
        }

        sb.append("</center></html>");
        return sb.toString();
    }
}
