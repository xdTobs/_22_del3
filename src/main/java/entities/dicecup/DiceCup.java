package entities.dicecup;

public abstract class DiceCup {
    protected Die[] dice;
    public abstract void roll();

    public abstract int[] getDiceValues();

    public abstract boolean diceAreEqual();
    public abstract int getSum();
}
