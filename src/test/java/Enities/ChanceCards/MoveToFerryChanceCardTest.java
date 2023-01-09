package Enities.ChanceCards;

import Controller.BasicUserIO;
import Controller.GameController;
import Controller.TestUserIO;
import Enities.DiceCup.DiceCup;
import Enities.DiceCup.TestDie;
import Enities.Fields.*;
import Enities.GameBoard;
import Enities.GameBoardTest;
import Language.Message;
import View.TestView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveToFerryChanceCardTest {

    @Test
    void executeCardAction() {
        Field[] fields = new Field[4];
//                            case ("street") -> new Street(line);
//                case ("tax") -> new Tax(line);
//                case ("jail") -> new Jail(line);
//                case ("gotoJail") -> new GoToJail(line);
//                case ("chance") -> new ChanceField(line);
//                case ("refugee") -> new Parking(line);
//                case ("start") -> new Start(line);
//                case ("brewery") -> new Brewery(line);
//                case ("ferry") -> new Ferry(line);
        fields[0] = FieldTest.getStartFieldDebug();
        fields[1] = new ChanceField("Prøv lykken,2, chance,,,,,,,,");
        fields[2] = new Brewery("Tuborg Squash,12, brewery,3000,0,100,200,,,,");
        fields[3] = new Ferry("Helsingør - Helsingborg,5, ferry,4000,0,500,1000,2000,4000,,");


        DiceCup diceCup = new DiceCup(new TestDie[]{new TestDie(1), new TestDie(0)});
        TestUserIO testUserIO = TestUserIO.debugSetup();
//    public GameBoard(DiceCup diceCup, Field[] fields, UserIO userIO, int playerCount) {
        List<ChanceCard> cards = new ArrayList<>();
        cards.add(new MoveToFerryChanceCard("This is a test ferry chance card."));
        Deck deck = new Deck(cards);
        GameBoard gameBoard = new GameBoard(diceCup, fields, deck, testUserIO, 2);

        GameController gameController = new GameController(new TestView(), testUserIO, gameBoard);

        gameController.playTurn(gameBoard.getCurrentPlayer());
        assertEquals(3, gameBoard.getCurrentPlayer().getPosition());

    }
}