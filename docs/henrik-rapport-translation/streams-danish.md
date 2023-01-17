Vi har valgt at bruge os af streams i vores kode. Vi kan sammenligne to kode eksempler her. Det første kommer fra vores DiceCup klasse. Vi har valgt at skrive den på følgende måde:

DEICEAREEQUAL 1

En tilsvarende imperativ løsning ser ud på følgende måde:

DICEAREEQUAL 2

Vi fandt følgende fordele ved at gøre dette:

Man undgår "off by one" errors. Det er nemmere at forstå, hvad vores metode gør, ved at læse den.

Punkt 2 kan forklares på følgende måde:

```
Kod 						Forklaring
Arrays.stream(dice) 		Streamen af følgende array
allMatch() 					Returnerer "true" hvis samtlige predikat er sande
die -> die.equals(dice[0]) 	Predikatet er, at die er lig med første terning
```

En ulempe, vi oplevede, var at for dem, der ikke har arbejdet med funktionell kode, kan det tage lidt tid at vænne sig til det. Derfor besluttede vi at ikke bruge funktionel kode i de centrale dele af koden. I stedet lod vi det være begrænset til små metoder. Disse kan man bruge som "black box" metoder uden at skulle grave sig ned i implementeringsdetaljer.
