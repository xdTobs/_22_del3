package Enities.DiceCup;

import java.lang.Math;
import java.util.Arrays;

//Terningen udviklet med brug af ideer og kode fra Java Software Solutions: Foundations of Program design, 9th Edition, listing 4.2
public class DieSixSided extends Die {
    //Private attributter for vores terninger
    private int max = 6;

    public DieSixSided() {
        this.max = max;
        roll();
    }

    public DieSixSided(int max) {
        if (max < 1) {
            throw new IllegalArgumentException("Can't create die with a max value less than 1.");
        }
        this.max = max;
        roll();
    }

    //Terningerne bygges ud fra samme princip som TestDice, da vores test viser en normalfordeling
    @Override
    public void roll() {
        faceValue = (int) (Math.random() * max + 1);
    }

    //Get-metode til at se den nuværende faceValue af terningerne
    @Override
    public int getFaceValue() {
        return faceValue;
    }

}
