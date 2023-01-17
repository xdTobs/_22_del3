Vi har tit valgt at bruge os af streams i vores kode. Vi kan sammenligne to kode eksempler her. Det første kommer fra vores \verb|DiceCup| klasse. Vi har valgt at skrive den på følgende måde:
\begin{lstlisting}[language=Java, caption=CAPTION!!!]
public boolean diceareequal() {
if (dice.length < 2) {
return false;
}
// equals is implemented in die class.  
 return arrays.stream(dice).allmatch(die -> die.equals(dice[0]));
}
\end{lstlisting}

\noindent En motsvarande imperativ lösning ser ut på följande sätt.

\begin{lstlisting}[language=Java, caption=CAPTION!!!]
public boolean diceAreEqual() {
if (dice.length < 2) {
return false;
}
for (int i = 1; i < dice.length; i++) {
if (!dice[i].equals(dice[0])) {
return false;
}
}
return true;
}
\end{lstlisting}

\noindent Vi fann följande fördelar med att göra detta.
\begin{itemize}
\item Man undgår off by one errors.
\item Det er meget nemmer at forstå hvad vores metode gør, gennem at læse den.
\end{itemize}

\noindent Punk två förklaras lättas på följande sätt:
\begin{center}
\begin{tabular}{ | l | l | l |}
\hline
Kod & Förklaring \\ [0.5ex]
\hline
\verb|Arrays.stream(dice)| & streama följande array\\
\verb|allMatch()| & \verb|return true| om samtliga predikat är sanna \\  
 \verb|die -> die.equals(dice[0])|& predikatet är att die \verb|equals| första tärningen\\
\hline
\end{tabular}
\end{center}

\noindent En nackdel som vi upplevde var att för dem som inte arbetat med liknande kod för kan det ta lite tid att vänja sig. Därför lät vi bli att använda funktionell kod i de central delarna av koden. Vi lät det istället vara avgränsat till små metoder. Dessa kan man använda kalla som black box metoder utan att behöva gräva ner sig i implementationsdetaljer.
