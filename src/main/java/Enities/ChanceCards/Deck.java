package Enities.ChanceCards;


import Enities.GameBoard;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<ChanceCard> cards;

    public Deck(List<ChanceCard> cards) {
        this.cards = cards;
    }

    //reads numbers from string
    public static Integer[] numberReader(String s) {
        StringBuilder numberBuilder = new StringBuilder();
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                numberBuilder.append(c);
            } else if (numberBuilder.length() > 0) {
                numbers.add(Integer.parseInt(numberBuilder.toString()));
                numberBuilder = new StringBuilder();
            }
        }
        if (numberBuilder.length() > 0) {
            numbers.add(Integer.parseInt(numberBuilder.toString()));
        }
        Integer[] numbersAsInt = new Integer[numbers.size()];
        numbers.toArray(numbersAsInt);

        return numbersAsInt;
    }

    public static Deck setup() {
        var inputStream = GameBoard.class.getClassLoader().getResourceAsStream("chanceCardsEnglish.txt");
        if (inputStream == null) {
            throw new IllegalStateException("InputStream should not be null");
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        List<ChanceCard> cards = new ArrayList<>();
        List<String> content = bufferedReader.lines().toList();
        for (int i = 0; i < 2; i++) {

            Integer[] numbers = numberReader(content.get(i));

            cards.add(new PayPerPropertyChanceCard(numbers[0], numbers[1], content.get(i)));
        }
        for (int i = 2; i < 12; i++) {
            Integer[] numbers = numberReader(content.get(i));
            cards.add(new ChangeBalChanceCard(-numbers[0], content.get(i)));
        }
        for (int i = 12; i < 24; i++) {
            Integer[] numbers = numberReader(content.get(i));
            cards.add(new ChangeBalChanceCard(numbers[0], content.get(i)));
        }
        for (int i = 24; i < 25; i++) {
            Integer[] numbers = numberReader(content.get(i));
            cards.add(new ChangeBalConditionalChanceCard(numbers[0], numbers[1], content.get(i)));
        }
        for (int i = 25; i < 28; i++) {
            Integer[] numbers = numberReader(content.get(i));
            cards.add(new ChangeBalFromPlayersChanceCard(numbers[0], content.get(i)));
        }
        for (int i = 28; i < 31; i++) {
            String[] words = content.get(i).split(" ");
            if (words[0].equals("Advance") || words[3].equals("frem")) {
                cards.add(new MoveSpacesChanceCard(3, content.get(i)));
            } else {
                cards.add(new MoveSpacesChanceCard(-3, content.get(i)));
            }
        }
        for (int i = 31; i < 33; i++) {
            cards.add(new MoveToFerryChanceCard(content.get(i)));
        }
        int[] indexes = new int[]{0, 0, 37, 15, 24, 32, 19, 39};
        for (int i = 33; i < 41; i++) {
            // TODO find the right indexes from line 49-56 in englishMessages.txt
            // There is no line 49-56 in that file??? /Henrik
            cards.add(new MoveToFieldChanceCard(indexes[i - 33], content.get(i)));
        }
        for (int i = 41; i < 42; i++) {
            cards.add(new MoveToBreweryChanceCard(content.get(i)));
        }
        for (int i = 42; i < 44; i++) {
            cards.add(new GetOutOfJailChanceCard(content.get(i)));

        }
        for (int i = 44; i < 46; i++) {
            cards.add(new GoToJailChanceCard(content.get(i)));
        }
        shuffleCards(cards);
        return new Deck(cards);
    }

    public static void shuffleCards(List<ChanceCard> cards) {
        Collections.shuffle(cards);
    }

    public void pullCard() {
        ChanceCard card = cards.remove(0);
        cards.add(card);
    }

    public ChanceCard takeChanceCard() {
        return cards.remove(0);
    }

    public int getSize() {
        return cards.size();
    }

    public void putCardBack(ChanceCard chanceCard) {
        cards.add(chanceCard);
    }
}



