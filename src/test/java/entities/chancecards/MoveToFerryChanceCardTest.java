package entities.chancecards;

import controller.GameController;
import controller.TestUserIO;
import entities.Player;
import entities.PlayerTest;
import entities.dicecup.RandomDiceCup;
import entities.dicecup.TestDie;
import entities.fields.*;
import entities.GameBoard;
import view.TestView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveToFerryChanceCardTest {

    @Test
    void Positive_MoveToFerryChanceCardTest() {
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
//        fields[0] = FieldTest.getStartFieldDebug();
//        fields[1] = new ChanceField("Prøv lykken,2, chance,,,,,,,,", 2);
//        fields[2] = new Brewery("Tuborg Squash,12, brewery,3000,0,100,200,,,,", Integer.parseInt(key[3]));
//        fields[3] = new Ferry("Helsingør - Helsingborg,5, ferry,4000,0,500,1000,2000,4000,,", Integer.parseInt(key[3]));

        fields[0] = new ChanceField("Prøv lykken,2, chance,,,,,,,,");
        fields[1] = new Ferry("Helsingør - Helsingborg",4000,new int[]{500,1000,2000,4000});
        fields[2] = new Ferry("Helsingør - Helsingborg",4000,new int[]{500,1000,2000,4000});
        fields[3] = new Ferry("Helsingør - Helsingborg",4000,new int[]{500,1000,2000,4000});
        RandomDiceCup randomDiceCup = new RandomDiceCup(new TestDie[]{new TestDie(1), new TestDie(2)});
        TestUserIO testUserIO = TestUserIO.debugSetup();
//    public GameBoard(DiceCup diceCup, Field[] fields, UserIO userIO, int playerCount) {
        List<ChanceCard> cards = new ArrayList<>();
        cards.add(new MoveToFerryChanceCard("This is a test ferry chance card."));
        Deck deck = new Deck(cards);
        Player[] players = PlayerTest.getTwoDebugPlayers(30000);
        GameBoard gameBoard = new GameBoard(randomDiceCup, fields, deck, testUserIO, players);

        GameController gameController = new GameController(new TestView(), testUserIO, gameBoard);

        gameController.playTurn(gameBoard.getCurrentPlayer());
        assertEquals(3, gameBoard.getCurrentPlayer().getPosition());

    }
}