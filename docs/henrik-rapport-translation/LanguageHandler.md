### LanguageHandler

Vi ville hitta ett effektiv och robust sätt att få våra medelanden till vår output. I tidigare projekt har vi upplevt
att det rätt fort blir väldigt krångligt med text handtering, när man ska implementera möjligheten för översättningar.
Efter samtal med Christian, om just detta, så fann vi fram till en lösning som underlättat vår process något enorm.

I tidigare projekt har vi bara haft en klass som läser in en CSV fil som vi sedan kan be om olika values från.

Här är ett exempel på hur vår kod såg ut tidigare.

```java
    view.showMessage(currentPlayer.getName() + " " + language.languageMap.get("rollDiceMsg"));
```

Att manuellt concatenate strings blir värre och värre ju större projektet blir. Vi satte oss ner och gjorde om det hela
från grunden, och landade i det här.

DIAGRAM

CAPTION: Detta diagram beskriver samtliga delar i vårt language package, men många metoder och enum alternativ har
skurits väck.

Vår GuiView har en referens till en LanguageHandler och genom den kan den få åtkomst till detta nu valda språkets
texter.

Man skapar en language handler genom att kalla `getLanguageHandler(Language.SWEDISH)`. Denna enum innehåller alla
tillgängliga språk. Detta gör vårt system mer robust, och när vi skriver kod, så kan vi genom autocompletion se vilka
språk som är tillgängliga. Det är omöjligt för oss att be om att skapa ett spel med `Language.GERMAN`. Det kommer ge oss
ett compile time error. När vi startar spelet så frågas spelaren efter val av språk. Valen som spelaren ges kommer från
metoden `getLanguages()`. Den returnerar värdet på vår `enum Language`.

LanguageHandler kommer att leta rätt på den korrekta csv filen, och kommer att gå igenom den och sätta
vår `EnumMap <Message.Type, String> languageMap` till de rätta värdena.

Så här ser datan i vår csv fil ut.

```csv
GAME_OVER_MESSAGE:* has won the game! The order of the rest of the players was: *. Choose yes to quit game.
```

`GAME_OVER_MESSAGE` är namnet på översättningen, och skall ha samma namn som tillsvarande language enum constant.
`*` är en placeholder som vi använder, för att senare, i vår Message klass kunna sätta in argument.

Vi har byggt in en del error handling i skapandet av vår languageMap.

```java
private static EnumMap<Message.Type, String> createDictionary(String resource) {
        var bufferedReader = new BufferedReader(new InputStreamReader(LanguageHandler.class.getClassLoader().getResourceAsStream(resource)));
        // If we need to split text in englishMessages we will use * for split.
        EnumMap<Message.Type, String> dictionary = new EnumMap<>(Message.Type.class);
        // Our csv file is parsed.
        for (String line : bufferedReader.lines().toList()) {
            String[] split = line.split(":", 2);
            Message.Type type = Message.Type.valueOf(split[0]);
            dictionary.put(type, split[1]);
        }
        List<Message.Type> missingTypes = Arrays.stream(Message.Type.values()).filter(type -> !dictionary.containsKey(type)).toList();
        if (missingTypes.size() != 0) {
            String s = missingTypes.stream().map(Enum::toString).reduce("", (acc, type) -> acc + type);
            throw new RuntimeException("LanguageMap is missing the following keys: " + s);
        }
        
        if (dictionary.size() != Message.Type.values().length) {
            String s = dictionary.keySet().stream().map(Enum::toString).reduce("", (acc, type) -> acc + type);
            throw new RuntimeException("LanguageMap has too many keys: " + s);
        }
        return dictionary;
    }
}
```

Denna kod har gör att det endast är möjligt att köra spelet med en csv fil som har en översättning för varje enum
constant, varken mer eller mindre. 

