package entities.chancecards;

public class MoveToFieldChanceCard extends ChanceCard {
    int fieldIndex;

    public MoveToFieldChanceCard(int fieldIndex, String desc) {
        this.fieldIndex = fieldIndex;
        this.desc = desc;
    }

    @Override
    public void executeCardAction(ChanceAction chanceAction) {
        chanceAction.moveToField(fieldIndex);
        chanceAction.printDescription(desc);
    }
}
