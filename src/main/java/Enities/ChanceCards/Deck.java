package Enities.ChanceCards;

import Enities.Fields.Field;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private ArrayList<ChanceCard> cards = new ArrayList<>();

    public Deck() {
        cards = new ArrayList<>();
        List<String> content;
        try {
            content = Files.readAllLines(Path.of("docs/chancecards.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

    private void shuffleCards() {
        Collections.shuffle(cards);
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



