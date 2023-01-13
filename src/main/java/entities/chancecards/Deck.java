package entities.chancecards;


import entities.GameBoard;

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
        for(String s : content){
            String[]split = s.split(";");
            Integer[] numbers = numberReader(s);


            switch (split[1]) {
                case ("PayPerProperty") -> cards.add(new PayPerPropertyChanceCard(numbers[0], numbers[1], split[0]));
                case ("ChangeBalNeg") -> cards.add(new ChangeBalChanceCard(-numbers[0], split[0]));
                case ("ChangeBalPos") -> cards.add(new ChangeBalChanceCard(numbers[0], split[0]));
                case("ChangeBalConditional") -> cards.add(new ChangeBalConditionalChanceCard(numbers[0], numbers[1], split[0]));
                case("ChangeBalFromPlayers") -> cards.add(new ChangeBalFromPlayersChanceCard(numbers[0], split[0]));
                case("MoveSpaces") -> cards.add(new MoveSpacesChanceCard(numbers[0], split[0]));
                case("MoveToBrewery") -> cards.add(new MoveToBreweryChanceCard(split[0]));
                case("MoveToField") -> cards.add(new MoveToFieldChanceCard(Integer.parseInt(split[2]), split[0]));
                case("MoveToFerry") -> cards.add(new MoveToFerryChanceCard(split[0]));
                case("GetOutOfJailFree") -> cards.add(new GetOutOfJailChanceCard(split[0]));
                case("GoToJail") -> cards.add(new GoToJailChanceCard(split[0]));
            }
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



