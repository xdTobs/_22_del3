package entities.chancecards;

import controller.GameController;
import controller.TestUserIO;
import entities.GameBoard;
import entities.dicecup.DiceCup;
import entities.dicecup.TestDie;
import entities.fields.*;
import org.junit.jupiter.api.Test;
import view.TestView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetOutOfJailChanceCardTest {

    @Test
    void Positive_GetOutOfJailChanceCardTest() {
        Field[] fields = new Field[4];
        fields[0] = new Start("Start");
        fields[1] = new ChanceField("Test Chance");
        fields[2] = new GoToJail("Go to jail");
        fields[3] = new Jail("Jail");


        DiceCup diceCup = new DiceCup(new TestDie[]{new TestDie(1), new TestDie(0)});
        TestUserIO testUserIO = TestUserIO.debugSetup();
        List<ChanceCard> cards = new ArrayList<>();
        cards.add(new GetOutOfJailChanceCard("This is a get out of jail chance card."));
        //check hvor mange chancekort spiller har.
        Deck deck = new Deck(cards);
        GameBoard gameBoard = new GameBoard(diceCup, fields, deck, testUserIO, 2);
        GameController gameController = new GameController(new TestView(), testUserIO, gameBoard);
        gameController.playTurn(gameBoard.getCurrentPlayer());
        gameController.playTurn(gameBoard.getCurrentPlayer());
        gameController.playTurn(gameBoard.getCurrentPlayer());


        assertEquals(0, gameBoard.getCurrentPlayer().getPosition());
    }
}