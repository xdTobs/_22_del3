package entities.chancecards;

public class MoveToFieldChanceCard extends ChanceCard {
    int fieldIndex;

    public MoveToFieldChanceCard(int fieldIndex, String description) {
        super(description);
        this.fieldIndex = fieldIndex;
    }

    @Override
    public void executeCardAction(ChanceAction chanceAction) {
        chanceAction.moveToField(fieldIndex);
        super.executeCardAction(chanceAction);
    }
}
