Vores udgangspunkt var at finde en praktisk måde at håndtere User Input og Output. Vi valgte at bruge et interface,
BasicUserIO, til dette.

En alternativ løsning ville være at skrive IO-metoder i View-interfacet, så ville vores GuiView være tvunget til at
håndtere IO, men ifølge GRASP, og sundt fornuft, er det bedre at lade ting gøre en ting meget godt, i stedet for to ting
ret dårligt. På den måde undgår vi både såkaldt "spaghetti-kode", men det bliver også nemmere at forstå vores kodes
struktur. Hvorfor skal en View, altså noget der er til for at vise ting, kunne håndtere Input og Output?

Lad mig tydeliggøre dette med et eksempel. En iPad er en View, der også håndterer IO, men det betyder ikke, at alle
skærme besidder denne egenskab. Vores computerskærme kan ikke styres med touch. Der bruger vi i stedet tastatur og mus,
dvs. et separat Input-system. Output håndteres af skærmen og højtalere, primært.

Vores klasse GuiView er hovedsageligt en View. Vi implementerer BasicUserIO, hvilket sørger for kommunikationen mellem
vores language package og resten af vores system.

Med dette i baghovedet valgte vi at give håndteringen af IO til interfacet BasicUserIO. Alle, der implementerer dette,
kan modtage og give den input, som vi har brug for som input i vores UserIO-klasse. UserIO-klassen vil blive forklaret
på et andet sted i rapporten, under adapter pattern.

I vores færdige produkt til kunden er GuiView både vores View og BasicUserIO. I mange af vores tests har vi derimod
valgt at adskille på View og BasicUserIO. Vores TestView er kun et tomt skal, der næsten ikke gør noget, fordi vi ikke
er interesserede i at vise noget visuelt i testene. Vi ønsker at se, hvordan spillets "model" ændres gennem forudbestemt
input, f.eks. hvis spilleren svarer ja til at købe en vej, givet en forudbestemt input, vores TestDie

Vi kan dekorere klasser, med et interface, der håndterer grundlæggende IO, og deraf navnet, "Decorator pattern".

DIAGRAM TEST
Sådan ser vores struktur for View og IO ud ved testning.

DIAGRAM REAL
Sådan ser vores struktur for View og IO ud i den endelige produkt.

Disse to diagrammer viser de forskellige måder, vi bruger vores interfaces på i tests i forhold til selve spillet. Hvis
vi havde lagt IO-metoder i View, ville vi have været tvunget til at implementere alle metoder i vores TestView. Det
ville have været overkommeligt i et så lille projekt som Matador, men også her har det gjort det nemmere for os. Fordi
vi ved, at vi ikke er interesseret i View i vores tests, kan vi undlade at fokusere på den og i stedet fokusere på det,
vi ved, vil være relevant, nemlig hvilken input vores GameController modtager.

Det, I ser i diagrammet, er en forenklet billede af, hvad vi har valgt at gøre i koden.
