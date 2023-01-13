package entities.chancecards;

import controller.GameController;
import controller.TestUserIO;
import entities.GameBoard;
import entities.Player;
import entities.PlayerTest;
import entities.Utils;
import entities.dicecup.DiceCup;
import entities.dicecup.TestDie;
import entities.fields.*;
import org.junit.jupiter.api.Test;
import view.TestView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveToBreweryChanceCardTest {

    @Test
    void Positive_MoveToBreweryChanceCardTest() {

        TestUserIO testUserIO = TestUserIO.debugSetup();
        GameBoard gameBoard = Utils.simpleBoard(testUserIO);
        GameController gameController = new GameController(new TestView(), testUserIO, gameBoard);
        //"Playing" the test
        gameController.playTurn(gameBoard.getCurrentPlayer());
        //Assert statement
        assertEquals(3, gameBoard.getCurrentPlayer().getPosition());
    }
}