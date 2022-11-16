package ChanceCards;

import Controllers.GUI_Controller;

import java.util.ArrayList;

public class Deck {
    Chance[] cards;

    public Deck(GUI_Controller gui_controller) {
        ArrayList<Chance> cards = new ArrayList<>();
        /*cards.add(new moveChance(
                // TODO implement i
                LanguageHandler.moveToMsg(3) + " " + gameBoard.getFields()[3].getTitle(),
                gui_controller,
                gameBoard.getFields()[2])
        );*/
        cards.add(new MoveChance(gui_controller, 2));
        this.cards = cards.toArray(new Chance[0]);
    }

    public Chance pullCard() {
        return cards[(int) (Math.random() * cards.length)];
    }
}
