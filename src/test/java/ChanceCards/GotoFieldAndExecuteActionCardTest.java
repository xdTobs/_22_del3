package ChanceCards;

import Enities.Fields.Field;
import Enities.Fields.Street;
import Enities.GameBoard;
import Enities.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GotoFieldAndExecuteActionCardTest {

    GameBoard gameBoard;
    GotoFieldAndExecuteActionCard card;
    Field field;

    @BeforeEach
    void setUp() {
        gameBoard = new GameBoard();
        gameBoard.createPlayers(2);
        Field field = gameBoard.getField(1);
        this.card = new GotoFieldAndExecuteActionCard(field);
    }

    @Test
    void executeCardAction() {
        Player p1 = gameBoard.getCurrentPlayer();
        int prevBalance = p1.getBalance();
        card.executeCardAction(gameBoard);
        int currBalance = p1.getBalance();
        assertEquals(prevBalance, currBalance);
    }

    @Test
    void executeCardActionAlreadyOwned() {

    }
}