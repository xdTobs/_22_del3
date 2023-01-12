package entities.dicecup;

public abstract class Die {
    int faceValue;

    public Die() {
        roll();
    }

    // faceValue should be set in roll.
    abstract void roll();

    //Get-metode til at se den nuværende faceValue af terningerne
    int getFaceValue() {
        return faceValue;
    }

    /**
     * Returns true if dice are of the same value. Sibling classes can also return true if face value is equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Die die)) return false;
        return faceValue == die.faceValue;
    }
}
