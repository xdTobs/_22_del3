package ChanceCards;

import Enities.Fields.Street;
import Enities.GameBoard;

import java.util.ArrayList;

/**
 * Deck of chance cards
 */
public class Deck {
    // Let's do 4 cards for now. All movechance cards.
    ChanceCard[] cards;

    public Deck(GameBoard gameBoard) {
        this.cards = initCards(gameBoard);
    }

    public ChanceCard[] initCards(GameBoard gameBoard) {
        ArrayList<ChanceCard> cards = new ArrayList<>();
//        cards.add(new PickStreetChanceCard(new Street[]{gameBoard.getStreet(1), gameBoard.getStreet(2)}));
        var s1 = gameBoard.getStreet(1);
        var s2 = gameBoard.getStreet(2);
        var pickCard = new PickStreetChanceCard(new Street[]{s1, s2});
        cards.add(pickCard);
        return cards.toArray(new ChanceCard[0]);
    }

    public ChanceCard pullCard() {
        return cards[(int) (Math.random() * cards.length)];
    }

    public void removeCard(ChanceCard c) {
//        List<ChanceCard> temp = new ArrayList<>(Arrays.asList(cards));
//        temp.remove(c);
//        if (temp.size() == 0) cards = initCards();
//        else cards = temp.toArray(new ChanceCard[0]);
    }
}



