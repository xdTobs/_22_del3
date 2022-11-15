package Enities;

import java.lang.Math;
//Terningen udviklet med brug af ideer og kode fra Java Software Solutions: Foundations of Program design, 9th Edition, listing 4.2


public class Die {
    //Private attributter for vores terninger
    private int Max = 6;

    private int faceValue;

    //Konstruktør for terningerne
    public Die() {
        faceValue = 1;
    }
    public Die(int max) {
        faceValue = 1;
        this.Max = Math.max(max,1);
    }

    //Terningerne bygges ud fra samme princip som TestDice, da vores test viser en normalfordeling
    public void roll() {
        faceValue = (int) (Math.random() * Max + 1);

    }

    //Get-metode til at se den nuværende faceValue af terningerne
    public int getFaceValue() {
        return faceValue;
    }

    //Set-metode til ændre værdien af faceValue1 og faceValue2
    public int setFaceValue(int x) {
        faceValue = x;
        return x;
    }


}
