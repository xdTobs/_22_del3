package Enities.DiceCup;

public class TestDie extends Die {

    /**
     * Die with constant value for testing.
     *
     * @param faceValue
     */
    public TestDie(int faceValue) {
        this.faceValue = faceValue;

    }

    @Override
    void roll() {
    }

    public static void main(String[] args) {
        Die d0 = new TestDie(6);
        Die d1 = new TestDie(6);
        Die d2 = new TestDie(3);
        System.out.println(d0.equals(d1));
        System.out.println(d0.equals(d2));
    }
}
