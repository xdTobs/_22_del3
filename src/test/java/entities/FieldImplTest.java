package entities;

import controller.BasicUserIO;
import controller.GameController;
import controller.UserIO;
import controller.View;
import entities.dicecup.DiceCup;
import entities.dicecup.TestDie;
import entities.fields.Field;
import language.LanguageController;
import language.Message;
import org.junit.jupiter.api.Test;
import view.GuiView;
import view.TestView;

import static org.junit.jupiter.api.Assertions.*;

class FieldImplTest {

    @Test
    void buyHouseProcess() {
        
    }
    @Test
  void gotoJail() {
      // gotoJail is on square 3 in the test.
      DiceCup dc = new DiceCup(new TestDie[]{new TestDie(1), new TestDie(2)});
        Field[] fields = GameBoard.getDefaultFields();
        GuiView guiView = GuiView.setup(fields);

      BasicUserIO userIO = new BasicUserIO() {
          @Override
          public int promptChoice(Message message, Message... choices) {
              return 0;
          }

          @Override
          public int promptRange(Message message, int min, int max) {
              return 0;
          }

          @Override
          public void showMessage(Message message) {

          }
    };
        GameBoard gameBoard = new GameBoard(dc,fields,userIO,2);
      View view = new TestView();

      GameController gameController = new GameController(view, userIO,gameBoard);
      gameController.resetPlayerPositions();
      // player 2 never moves.
      // turn 0 roll
      // turn 1 jail
      // turn 2 jail
      // turn 3 jail
      // turn 4 not jail
      gameController.playTurn(gameBoard.getCurrentPlayer());

      for (int i = 0; i < 3; i++) {
          assertTrue(gameBoard.getCurrentPlayer().isJailed());
          gameController.playTurn(gameBoard.getCurrentPlayer());
      }
      assertFalse(gameBoard.getCurrentPlayer().isJailed());
  }
    
}