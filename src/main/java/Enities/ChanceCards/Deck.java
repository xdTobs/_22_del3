package Enities.ChanceCards;

import Enities.Fields.Field;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private final ArrayList<ChanceCard> cards = new ArrayList<>();

    public Deck(Field[] fields) {
        addPickStreetCards(fields);
        addGotoFieldCards(fields);
        addGetOutOfJailCards();
        addBalanceChanceCards();
        shuffleCards();
    }

    private void shuffleCards() {
        Collections.shuffle(cards);
    }

    private void addBalanceChanceCards() {
        cards.add(new BalanceChanceCard(2));
        cards.add(new BalanceChanceCard(-2));

    }

    private void addGetOutOfJailCards() {
        cards.add(new GetOutOfJailChanceCard());
    }

    private void addPickStreetCards(Field[] fields) {
        // Cards from spec
        cards.add(new PickStreetChanceCard(new Field[]{fields[1], fields[2], fields[7], fields[8]}));
        cards.add(new PickStreetChanceCard(new Field[]{fields[13], fields[14]}));
        cards.add(new PickStreetChanceCard(new Field[]{fields[16], fields[17]}));
        cards.add(new PickStreetChanceCard(new Field[]{fields[19], fields[20], fields[10], fields[11]}));
        cards.add(new PickStreetChanceCard(new Field[]{fields[1], fields[2]}));
        cards.add(new PickStreetChanceCard(new Field[]{fields[1], fields[2], fields[10], fields[11]}));
        cards.add(new PickStreetChanceCard(new Field[]{fields[4], fields[5], fields[22], fields[23]}));


    }

    private void addGotoFieldCards(Field[] fields) {
        // Add chance card for going to start.
        cards.add(new GotoFieldAndExecuteActionCard(fields[0]));
        // Add chance card for going to skaterpark.
        cards.add(new GotoFieldAndExecuteActionCard(fields[10]));
        // Add chance card for going to strandpromenaden.
        cards.add(new GotoFieldAndExecuteActionCard(fields[23]));
    }

    public void pullCard() {
        ChanceCard card = cards.remove(0);
        cards.add(card);
    }

    public ChanceCard getLatestChanceCard() {
        return cards.get(0);
    }

    public int getSize() {
        return cards.size();
    }
}



