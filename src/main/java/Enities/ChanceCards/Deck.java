package Enities.ChanceCards;

import Enities.Fields.Field;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Matcher matcher;

        for (int i = 0; i < 2; i++) {

            matcher = Pattern.compile("\\d+").matcher(content.get(i));

            cards.add(new PayPerPropertyChanceCard(Integer.parseInt(matcher.group()),Integer.parseInt(matcher.group()),content.get(i)));
        }
        for (int i = 2; i <24 ; i++) {
            String numberOnly= content.get(i).replaceAll("[^0-9]", "");
            cards.add(new ChangeBalChanceCard(Integer.parseInt(numberOnly), content.get(i)));
        }
        for (int i = 24; i < 25; i++) {
            String numberOnly= content.get(i).replaceAll("[^0-9]", ",");
            String[]prices = numberOnly.split(",");
            cards.add(new ChangeBalConditionalChanceCard(Integer.parseInt(prices[0]), Integer.parseInt(prices[1]), content.get(i)));
        }
        for (int i = 25; i < 28; i++) {
            String numberOnly= content.get(i).replaceAll("[^0-9]", "");
            cards.add(new ChangeBalFromPlayersChanceCard(Integer.parseInt(numberOnly),content.get(i)));
        }
        for (int i = 28; i < 31; i++) {
            String[] words = content.get(i).split(" ");
            if (words[0].equals("Advance")||words[3].equals("frem")){
                cards.add(new MoveSpacesChanceCard(3, content.get(i)));
            }else{
                cards.add(new MoveSpacesChanceCard(-3, content.get(i)));
                //TODO make sure you can go below 0. Also maybe better way to see if it moves forward or backward
            }
        }
        for (int i = 31; i < 33; i++) {
            cards.add(new MoveToBreweryChanceCard(content.get(i)));
        }
        int[] indexes = new int[]{0,0,0,0,0,0,0,0};
        for (int i = 33; i < 41; i++) {

            //TODO find the right indexes from line 49-56 in LanguageEnglish
            cards.add(new MoveToFieldChanceCard(indexes[i-33],content.get(i)));
        }
        for (int i = 41; i < 42; i++) {
            cards.add(new MoveToFerryChanceCard(content.get(i)));
        }
        for (int i = 42; i < 44; i++) {
            cards.add(new GetOutOfJailChanceCard(content.get(i)));

        }
        for (int i = 44; i < 46; i++) {
            cards.add(new GoToJailChanceCard(content.get(i)));
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



