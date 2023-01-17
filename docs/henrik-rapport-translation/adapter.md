Vi har valt att använda Adapter pattern. Den klass
som fungerar som vår adapter heter UserIO.

Genom användning av decorator pattern så har vi skapat en BasicUserIO, beskrivet härLÄNKATILLDECORATORPATTERN. Vi valde att skapa en IO som bara kan de mest grundläggande sakerna. Eftersom den inte ska kunna särskilt många saker så går den förhållandevis snabbt att implementera.

GuiView är den klass som implementerar BasicUserIO, i vår spel.

Eftersom BasicUserIO bara har väldigt basala metoder så kan det vara väldigt krångligt att kalla just den specifika metoden vi vill ha. Därför har vi valt att skapa en adapter som heter UserIO. Den fungerar som ett hölje kring Basic, och innehåller metoder som underlättar för oss. Kod som ofta upprepas, till exemepel att fråga efter ett ja eller en nej på en fråga, kan vi lägga in som en metod i UserIO, för att göra koden enklare att läsa.

Om vi ska fråga spelaren om han vill köpa ett fält, direkt till BasicUserIO, så ser det ut på följande sätt.

KODE EXEMPEL 1

Detta är alltför mycket och komplicerad kod för en så enkel fråga. Vi skapar därför vår adapter UserIO som ska
underlätta vår kod gentemot BasicUserIO.

Här är två möjliga lösningar, med hjälp av statiska hjälpmetoder i Message class och UserIO.

KODE EXEMPEL 2

Jag tror de flesta skulle hålla med om att denna kod är otroligt mycket lättare att läsa.

Vår UserIO klass innehåller en referens till en BasicUserIO. I spelet är det en referens till vår guiView. UserIO kallar bara de metoder som vi implementerat i guiView, då den implementerade BasicUserIO.

KODE EXEMPEL 3
