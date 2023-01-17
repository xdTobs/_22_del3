Utöver det Decorator pattern, som tidigare har beskrivits, så har vi valt att också använda Adapter pattern. Den klass
som fungerar som vår adapter heter UserIO.
Vi valde att skapa en IO som bara har de mest grundläggande sakerna i sig, som vi kallar BasicUserIO.
Den ska inte kunna mycket, och är därför enkel att implementera.
GuiView är den klass som implementerar BasicUserIO
Då BasicUserIO innehåller väldigt basala metoder så är den enkel att snabbt implementera.

Om vi ska fråga spelaren om han vill köpa ett fält, direkt till BasicUserIO, så ser det ut på följande sätt.

KODE EXEMPEL 1

Detta är alltför mycket och komplicerad kod för en så enkel fråga. Vi skapar därför vår adapter UserIO som ska
underlätta vår kod gentemot BasicUserIO.

UserIO implementar BasicUserIO, men utöver det så kan vi lägga till andra metoder i den. I slutändan så kallar alla
metoder i UserIO klassen bara en metod i BasicUserIO.  
I kombination statiska hjälpmetoder i klassen Message, så kan vi få en mycket enklare kod. Här är två möjliga lösningar.

KODE EXEMPEL 2

Jag tror de flesta skulle hålla med om att denna kod är otroligt mycket lättare att läsa.
