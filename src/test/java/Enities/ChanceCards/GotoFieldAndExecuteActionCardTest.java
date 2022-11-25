package Enities.ChanceCards;

import Enities.Fields.Street;
import Enities.GameBoard;
import Enities.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GotoFieldAndExecuteActionCardTest {

    GameBoard gameBoard;
    GotoFieldAndExecuteActionCard card;
    Street street;

    @BeforeEach
    void setUp() {
        gameBoard = new GameBoard();
        gameBoard.createPlayers(2);
        this.street = (Street) gameBoard.getField(1);
        this.card = new GotoFieldAndExecuteActionCard(street);
    }

    @Test
    void executeCardAction() {
        Player p1 = gameBoard.getCurrentPlayer();
        int prevBalance = p1.getBalance();
        card.executeCardAction(gameBoard);
        int currBalance = p1.getBalance();
        assertEquals(prevBalance, currBalance);
        assertEquals(p1.getPosition(), street.getPosition());
    }

    @Test
    void executeCardActionAlreadyOwned() {
        Player p1 = gameBoard.getCurrentPlayer();
        gameBoard.nextPlayer();
        Player p2 = gameBoard.getCurrentPlayer();
        // p2 buys field.
        p2.setPosition(street.getPosition());
        street.executeFieldAction(gameBoard);

        // p1 lands on field through card that is already owned.
        int p2PrevBalance = p2.getBalance();
        gameBoard.nextPlayer();
        int rent = street.getRent();
        int p1PrevBalance = p1.getBalance();
        card.executeCardAction(gameBoard);
        int p1CurrBalance = p1.getBalance();
        int p2CurrBalance = p2.getBalance();
        // p2 should have earned rent and p1 should have paid rent.
        assertEquals(p1PrevBalance, p1CurrBalance + rent);
        assertEquals(p2PrevBalance, p2CurrBalance - rent);


    }
}