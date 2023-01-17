package runconfig;

import controller.GameController;
import controller.UserIO;
import entities.GameBoard;
import entities.Player;
import entities.fields.Ferry;
import entities.fields.Field;
import entities.fields.RentableField;
import language.LanguageController;
import language.Message;
import view.GuiView;

import java.util.List;

public class Player1OwnsEverything {

    //shows gameover
    public static void main(String[] args) {
        Field[] fields = GameBoard.getDefaultFields();
        GuiView view = GuiView.setup(fields, LanguageController.getDefaultLanguageController());
        UserIO userIO = new UserIO(view);

        Player[]players = new Player[]{new Player("1",1),new Player("2",1),new Player("3",1),new Player("4",1)};
        GameBoard gameBoard = new GameBoard(fields, userIO, players);

        GameController game = GameController.setup(view, userIO, gameBoard);

        for(Field field : gameBoard.getFields()){
            if(field instanceof RentableField rf) {
                rf.setOwner(gameBoard.getCurrentPlayer());
                gameBoard.getOwnershipMap().get(gameBoard.getCurrentPlayer()).add(rf);
            }
        }
        game.playGame();
    }
}
