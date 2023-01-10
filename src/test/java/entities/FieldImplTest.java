package entities;

import controller.GameController;
import controller.TestUserIO;
import entities.chancecards.ChanceCard;
import entities.chancecards.Deck;
import entities.chancecards.MoveToFerryChanceCard;
import entities.dicecup.DiceCup;
import entities.dicecup.TestDie;
import entities.fields.*;
import org.junit.jupiter.api.Test;
import view.TestView;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FieldImplTest {

    static GameBoard debugSetupBuyHouseProcess() {

        Field[] fields = new Field[4];
        fields[0] = FieldTest.getStartFieldDebug();
        fields[1] = new Street("Rødovrevej,1, street,1200,1000,50,250,750,2250,4000,6000");
        fields[1].setPair(new FieldPair(Color.BLACK, new int[]{1, 2}));
        fields[2] = new Street("Hvidovrevej,2, street,1200,1000,50,250,400,750,2250,6000");
        fields[2].setPair(new FieldPair(Color.BLACK, new int[]{1, 2}));

        DiceCup diceCup = new DiceCup(new TestDie[]{new TestDie(1), new TestDie(0)});
        TestUserIO testUserIO = TestUserIO.debugSetup();
        Deck deck = new Deck(new ArrayList<>());
        return new GameBoard(diceCup, fields, deck, testUserIO, 2);

    }

    @Test
    void buyHouseProcess() {
        TestUserIO testUserIO = TestUserIO.debugSetup();
        GameBoard gameBoard = debugSetupBuyHouseProcess();
        GameController gameController = new GameController(new TestView(), testUserIO, gameBoard);

        gameController.playTurn(gameBoard.getCurrentPlayer());
        gameController.playTurn(gameBoard.getCurrentPlayer());
        // Player should have bought both streets at index 1 and 2, and should be able to buy houses.

        System.out.println("Done");
//
    }

    @Test
    void buyEmptyStreet() {
    }
}