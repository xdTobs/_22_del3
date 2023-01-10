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

import static org.junit.jupiter.api.Assertions.*;

class GoToJailChanceCardTest {

    @Test
    void executeCardAction() {
        Field[] fields = new Field[16];

        fields[0] = FieldTest.getStartFieldDebug();
        fields[1] = new ChanceField("Prøv lykken,2, chance,,,,,,,,");
        fields[2] = new Ferry("Helsingør - Helsingborg,5, ferry,4000,0,500,1000,2000,4000,,");
        fields[3] = new Ferry("Helsingør - Helsingborg,5, ferry,4000,0,500,1000,2000,4000,,");
        fields[4] = new Ferry("Helsingør - Helsingborg,5, ferry,4000,0,500,1000,2000,4000,,");
        fields[5] = new Ferry("Helsingør - Helsingborg,5, ferry,4000,0,500,1000,2000,4000,,");
        fields[6] = new Ferry("Helsingør - Helsingborg,5, ferry,4000,0,500,1000,2000,4000,,");
        fields[7] = new Ferry("Helsingør - Helsingborg,5, ferry,4000,0,500,1000,2000,4000,,");
        fields[8] = new Ferry("Helsingør - Helsingborg,5, ferry,4000,0,500,1000,2000,4000,,");
        fields[9] = new Ferry("Helsingør - Helsingborg,5, ferry,4000,0,500,1000,2000,4000,,");
        fields[10] = new Jail("I fængsel/På besøg,10, jail,,,,,,,,");
        fields[11] = new Ferry("Helsingør - Helsingborg,5, ferry,4000,0,500,1000,2000,4000,,");
        fields[12] = new Ferry("Helsingør - Helsingborg,5, ferry,4000,0,500,1000,2000,4000,,");
        fields[13] = new Ferry("Helsingør - Helsingborg,5, ferry,4000,0,500,1000,2000,4000,,");
        fields[14] = new Ferry("Helsingør - Helsingborg,5, ferry,4000,0,500,1000,2000,4000,,");
        fields[15] = new Ferry("Helsingør - Helsingborg,5, ferry,4000,0,500,1000,2000,4000,,");

        DiceCup diceCup = new DiceCup(new TestDie[]{new TestDie(1), new TestDie(0)});
        TestUserIO testUserIO = TestUserIO.debugSetup();
        List<ChanceCard> cards = new ArrayList<>();
        cards.add(new GoToJailChanceCard("This is a GoToJail chance card."));
        Deck deck = new Deck(cards);
        GameBoard gameBoard = new GameBoard(diceCup, fields, deck, testUserIO, 2);
        GameController gameController = new GameController(new TestView(), testUserIO, gameBoard);
        gameController.playTurn(gameBoard.getCurrentPlayer());

        assertEquals(10, gameBoard.getCurrentPlayer().getPosition());
    }
}