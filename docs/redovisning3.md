AT5: Som spiller, vil jeg gerne lande på et felt som er ejet af en anden spiller, sådan at spillet overfører leje fra
mig til spilleren der ejer feltet.

```
FieldImplTest.java
@DisplayName("If player lands on a street and it is owned  by a different player he should pay rent.")
void payRentToOwner() 

@DisplayName("If a player owns all pairs in a street pair the rent should be doubled, if there are no houses built.")
void payDoubleRentToOwner() 

@DisplayName("If a player is in Jail he should not be able to roll recieve rent.")
void noRentWhenInJailTest() 
```

AT13: Som spiller, vil jeg gerne lande på et "Prøv lykken"-felt, sådan at jeg trækker et chancekort og udfører
handlingen.

```
ChanceCardTest.java
```

AT15: Som spiller, vil jeg gerne komme i fængsel, hvis jeg slår ens tre gange i streg, sådan at ture aldrig kan blive
for lange.

```
GameControllerTest.java
void threeSameDieGoToJailTest() {
```
