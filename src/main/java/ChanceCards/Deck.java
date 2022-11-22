package ChanceCards;

import Enities.Fields.Field;
import Enities.Fields.Street;
import Enities.GameBoard;

import java.util.ArrayList;

/**
 * Deck of chance cards
 */
public class Deck {
    // Let's do 4 cards for now. All movechance cards.
    ChanceCard[] cards = new ChanceCard[7];

    public Deck(GameBoard gameBoard) {
        this.cards = initCards(gameBoard);
    }

    public Deck(Field[] fields) {
        // I add a pick street chance for all the streets just after corners.
        for (int i = 0; i < 4; i++) {
            Field s1 = fields[i * 6];
            Field s2 = fields[i * 6 + 1];
            ChanceCard pickCard = new PickStreetChanceCard(new Field[]{s1, s2});
            cards[i] = pickCard;
        }
        cards[4] = new GotoFieldAndExecuteActionCard(fields[0]);
        cards[5] = new GotoFieldAndExecuteActionCard(fields[10]);
        cards[6] = new GotoFieldAndExecuteActionCard(fields[23]);
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
        //TODO hvorfor er den fjernet?? Troede ikke vi brugte den.

    }
}



