package Controller;

import ChanceCards.ChanceCard;
import ChanceCards.Deck;
import ChanceCards.PickStreetChanceCard;
import Enities.Fields.Chance;
import Enities.Fields.Street;
import Enities.GameBoard;
import Enities.Player;
import Language.LanguageHandler;
import View.GUI_View;


/**
 * The type Game handler.
 */
public class GameHandler {
    private View.GUI_View view;

    private GameBoard gameBoard;

    public GameHandler(GUI_View view, GameBoard gameBoard) {
        // Remember to give the car a color, so p1 and p2 don't have same colors.
        this.view = view;
        this.gameBoard = gameBoard;
//        GUI_Car car1 = new GUI_Car();
//        car1.setPrimaryColor(Color.black);
//        GUI_Car car2 = new GUI_Car();
//        car2.setPrimaryColor(Color.white);
//        this.gui_controller = new GUI_Controller(new GUI_Player[]{this.players[0].getGuiPlayer(), this.players[1].getGuiPlayer()}, gameBoard.getGuiFields());


    }

    /**
     * Play game.
     */
    public void playGame() {
        // Moves all player to the start position.
        resetPlayerPositions();
        while (true) {
            Player currentPlayer = gameBoard.getCurrentPlayer();
            playTurn(currentPlayer);
            if (gameBoard.isGameover()) {
                // TODO We need to add correct winner message. A list from loser to winner. The one with most money is winner.
                view.showMessage(currentPlayer.getName() + " has lost!!!");
                break;
            }
            gameBoard.nextPlayer();
        }
    }

    public void playTurn(Player currentPlayer) {
        // If a player was jailed last turn he needs to pay a fine to get out or use a get out of jail free card.
        if (currentPlayer.isJailed()) {
            gameBoard.payFine(currentPlayer);
            view.showMessage(currentPlayer.getName() + LanguageHandler.jailMsg(currentPlayer.getName()));
        }

        // We roll our dice.
        boolean hasPassedStart = gameBoard.rollDieMovePlayer();
        view.showMessage(currentPlayer.getName() + " " + LanguageHandler.rollDiceMsg());
        if (hasPassedStart) {
            view.showMessage(LanguageHandler.passedStartMsg());
        }
        view.updatePlayerLocations(gameBoard.getPlayers());
        view.updateDie(gameBoard.getDiceCup());

        // We make an action according to what field the player landed on.

        //unoptimal way to get acces to GUI in chance card, but gui is the only way to get user input, which we need
        if (gameBoard.getFields()[gameBoard.getCurrentPlayer().getPosition()] instanceof Chance chanceCard){
            Deck deck =gameBoard.getCards();
            ChanceCard chance = deck.pullCard();
            if (chance instanceof PickStreetChanceCard pickStreetChanceCard){
                int choice = pickStreetChanceCard.chooseStreet(view.getGui(),currentPlayer, gameBoard.getFields());
                pickStreetChanceCard.setStreet((Street) gameBoard.getFields()[choice]);
                pickStreetChanceCard.executeCardAction(gameBoard.getPlayers(), currentPlayer);
            }
        }
        else{
            gameBoard.fieldAction(currentPlayer);

        }
        view.update(gameBoard.getDiceCup(), gameBoard.getPlayers(), gameBoard.getFields());


    }

    private void resetPlayerPositions() {
        gameBoard.resetPlayerPositions();
        view.update(gameBoard.getDiceCup(), gameBoard.getPlayers(), gameBoard.getFields());
    }
}

