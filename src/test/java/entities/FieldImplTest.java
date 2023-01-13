package entities;

import controller.*;
import controller.TestUserIO;
import entities.chancecards.Deck;
import entities.dicecup.RandomDiceCup;
import entities.dicecup.TestDie;
import entities.fields.*;
import language.Message;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import view.TestView;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FieldImplTest {

    @Test
    void buyHouseProcess() {
        RandomDiceCup dc = new RandomDiceCup(new TestDie[]{new TestDie(1), new TestDie(2)});
        Field[] fields = GameBoard.getDefaultFields();
        BasicUserIO userIO = new BasicUserIO() {
            @Override
            public int promptChoice(Message message, Message... choices) {
                //override the choice to always want to buy a house on the first avaliable street
                if (message.getType().equals(Message.selectHouse().getType()))
                    return 1;
                return 0;
            }

            @Override
            public int promptRange(Message message, int min, int max) {
                throw new RuntimeException("shouldnt be here in this test");
            }

            @Override
            public String promptString(Message message) {
                return null;
            }

            @Override
            public void showMessage(Message message) {

            }
        };
        UserIO testUserIO = new UserIO(userIO);
        Player[]players = PlayerTest.getTwoDebugPlayers(30000);
        GameBoard gameBoard = new GameBoard(dc, fields, testUserIO, players);
        View view = new TestView();

        GameController gameController = new GameController(view, testUserIO, gameBoard);
        gameController.resetPlayerPositions();

        //Hardcode giving the first pair of streets to player 1
        Street street1 = (Street) gameBoard.getField(1);
        street1.setOwner(gameBoard.getCurrentPlayer());
        Street street2 = (Street) gameBoard.getField(3);
        street2.setOwner(gameBoard.getCurrentPlayer());
        //also add this to the ownership map, since they are not bought through normal process
        gameBoard.getOwnershipMap().get(gameBoard.getCurrentPlayer()).add(street1);
        gameBoard.getOwnershipMap().get(gameBoard.getCurrentPlayer()).add(street2);
        //play a turn for player 1 and player 2, where you always buy all of the houses you can
        //since player 1 already owns a pair, they buy four houses and a hotel first turn, and player 2 pays rent for all of it
        for (int i = 0; i < 2; i++) {
            gameController.playTurn();
            gameBoard.nextPlayer();
        }
        //player2 balance should be 24000 since they pay rent with hotel for 6000
        assertEquals(24000, gameBoard.getPlayers()[1].getBalance());
        //player1 balance should be 26000 since they buy houses for 10000 and recieve 6000 rent
        assertEquals(26000, gameBoard.getPlayers()[0].getBalance());

    }

    @Test
    @DisplayName("If player lands on go to jail, he should get automatically moved to jail and his status should be set to jailed")
    void gotoJail() {
        Field[] fields = new Field[3];

        fields[0] = FieldTest.getStartFieldDebug();
        fields[1] = new GoToJail("Ga i fængsel,1, jail,,,,,,,,");
        fields[2] = new Jail("I fængsel/På besøg,2, jail,,,,,,,,");

        RandomDiceCup randomDiceCup = new RandomDiceCup(new TestDie[]{new TestDie(1), new TestDie(0)});
        TestUserIO testUserIO = TestUserIO.debugSetup();
        Deck deck = new Deck(new ArrayList<>());
        Player[]players = PlayerTest.getTwoDebugPlayers(30000);
        GameBoard gameBoard = new GameBoard(randomDiceCup, fields, deck, testUserIO, players);
        GameController gameController = new GameController(new TestView(), testUserIO, gameBoard);

        // Player should go one step, land on go to jail and then get moved to jail.
        gameController.playTurn();


        assertEquals(2, gameBoard.getCurrentPlayer().getPosition());
        assertTrue(gameBoard.getCurrentPlayer().isJailed());
    }

}