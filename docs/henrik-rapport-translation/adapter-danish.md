Vi har valgt at bruge Adapter pattern. Klassen, der fungerer som vores adapter, kaldes UserIO.

Gennem brug af decorator pattern har vi skabt en BasicUserIO, beskrevet her LÄNKATILLDECORATORPATTERN. Vi valgte at skabe en IO, der kun kan de mest grundlæggende ting og fordi den ikke skal kunne særlig mange ting, er den relativt nem og hurtig at implementere.

GuiView er klassen, der implementerer BasicUserIO i vores spil.

Da BasicUserIO kun har meget basale metoder, kan det være meget besværligt at udføre den specifikke operation, vi ønsker. Derfor har vi valgt at skabe en adapter, der hedder UserIO. Den fungerer som et skal omkring BasicUserIO, og indeholder metoder, der gør det nemmere for os at brug IO. IO, der ofte gentages, for eksempel at spørge efter et ja eller nej til et spørgsmål, kan vi lægge ind som en metode i UserIO for at gøre koden nemmere at arbejde med.

Hvis vi skal spørge spilleren, om han vil købe et felt, direkte til BasicUserIO, ser det sådan her ud:

KODE EKSEMPEL 1

Det er for meget kode og koden er for kompliceret for et så simpelt spørgsmål. Derfor bruger vi vores adapter UserIO, der skal understøtte vores kode i forhold til BasicUserIO.

Her er to mulige løsninger ved hjælp af statiske hjælpe metoder i Message class og UserIO.

KODE EKSEMPEL 2

Jeg tror, de fleste ville være enige om, at denne kode er meget lettere at læse.

Vores UserIO klasse indeholder en reference til en BasicUserIO. I spillet er det en reference til vores guiView. UserIO kalder kun de metoder, vi har implementeret i guiView, da den implementerer BasicUserIO.

KODE EKSEMPEL 3
