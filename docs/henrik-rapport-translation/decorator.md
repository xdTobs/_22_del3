Vår utgångspunkt var att hitta ett sätt att hantera User Input och Output. Vi valde att använda en abstract class
BasicUserIO till detta. En annan lösning vore att skriva IO-metoder i View-interface. Då skulle vår GuiView bli tvungen
att kunna hantera IO. Enligt GRASP, och sunt förnuft, så är det bättre att låta saker göra en sak väldigt bra, istället
för två saker rätt dåligt. Då undgår vi dels så kallad "spaghetti-kod", men det blir även att förstå vår kods struktur.
Varför ska en View, alltså något som är till för att visa saker, kunna ta emot Input och Output?

Låt mig förtydliga med ett exempel. En iPad är en View som också hanterar IO, men det betyder inte att alla skärmar
besitter denna egenskap. Våra datorskärmar kan inte styras med touch. Där använder vi istället tangentbord och mus,
d.v.s ett separat Input-system. Output hanteras av skärmen hanteras och högtalare, primärt.

Vår klass GuiView är huvudsakligen ett View. Vi implementerar BasicUserIO vilket kommer sörja för kommunikationen mellan
vår language package och resten av vårt system.

Med detta i bakhuvudet valde vi att ge denna uppgift till ett interfacet BasicUserIO. Alla som implementerar detta kan
ta emot och ge ifrån sig all input som vi behöver som input i vår UserIO klass. UserIO klassen kommer förklaras på en
annan plats i rapporten, under adapter pattern. I vår slutgiltiga produkt till kunden så är GuiView både vår View och
BasicUserIO. I många av våra test har vi valt att separera på View och BasicUserIO. Vår TestView är bara ett tomt skal
som nästan inte gör något, eftersom vi inte är intresserade av att visa något visuellt i testen. Vi vill se hur
spelets "model" förändras genom förbestämd input, spelaren svarar alltid ja på att köpa en gata, som respons på
förutbestämd output, tärningar som inte längre är slumpmässiga.

Vi kan dekorerar klasser, med ett interface som hanterar grundläggande IO, och därav namnet, "Decorator pattern".

DIAGRAM TEST
Så här ser vår struktur för View och IO ut vid testning.

DIAGRAM REAL
Så här ser vår struktur för View och IO ut i slutprodukten.

Dessa två diagram visar de olika sätt vi använder våra interfaces på i tests kontra själva spelet. Om vi hade lagt IO
metoder i View, så hade vi varit tvugna att implementera samtliga metoder i vår TestView. Det hade varit överkomligt i
ett så pass litet projekt som Matador, men även här så har det underlättat för oss. Eftersom vi vet att vi är
ointresserade av View i våra tests, så kan vi låta bli att fokusera på den och istället lägga fokus på det vi vet kommer
vara relevant, nämligen vilken input vår GameController mottar.

Det ni ser i diagrammet är en förenklad bild av vad vi valt att göra i koden.
