package Language;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

// We can use this class to implement different languages. For example, we can make translation variable be an array and then we can loop through it and print out the different translations, so the game is multi-lingual.
public class LanguageHandler {
    private static String[] fieldNames=new String[]{"Start", "Burgerbaren", "Pizzeriaet", "Chance", "Slikbutikken", "Iskiosken", "Fængsel", "Museet", "Biblioteket", "Chans", "Skaterparken", "Swimmingpoolen", "Parkering", "Spillehallen", "Biografen", "Chans", "Legetøjsbutikken", "Dyrehandlen", "Gå i fængsel", "Bowlinghallen", "Zoo", "Chans", "Vandlandet", "Strandpromenaden"};
    public HashMap<String,String> languageMap = new HashMap<>();


    public LanguageHandler() throws IOException {

        List<String> content = Files.readAllLines(Path.of("src/main/java/Language/LanguageEnglish"));
        for (String s : content){
            System.out.println(s);
            String[]keyValue = s.split(":",2);
            if(keyValue.length>1)
                languageMap.put(keyValue[0],keyValue[1]);
        }
    }

    public static String getFieldName(int i) {
        return fieldNames[i];
    }
}
