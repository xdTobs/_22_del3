package entities.fields;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Field {
    protected FieldPair pair;
    private int position;
    private String name;

    public Field(String s) {
        this.parse(s);
    }

    private static Set<FieldPair> fieldPairsFromField(List<RentableField> rentableFields) {
        Set<FieldPair> fieldPairSet = new HashSet<>();
        for (RentableField rentableField : rentableFields) {
            fieldPairSet.add(rentableField.getPair());
        }
        return fieldPairSet;
    }

    public static Set<FieldPair> findFieldPairsAllOwnedByPlayer(List<RentableField> currentPlayerOwnedFields) {
        Set<FieldPair> fieldPairSet = fieldPairsFromField(currentPlayerOwnedFields);

        List<Integer> ownedIds = currentPlayerOwnedFields.stream().map(field -> field.getPosition()).collect(Collectors.toList());
        Set<FieldPair> fieldPairsPlayerOwns = new HashSet<>();
        for (FieldPair fieldPair : fieldPairSet) {
            if (playerOwnsAllFieldsInFieldPair(fieldPair.getFieldIds(), ownedIds)) {
                fieldPairsPlayerOwns.add(fieldPair);
            }
        }
        return fieldPairsPlayerOwns;
    }

    public static boolean playerOwnsAllFieldsInFieldPair(int[] fieldPairIds, List<Integer> ownedIds) {
        for (int id :
                fieldPairIds) {
            if (!ownedIds.contains(id)) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {

    }

    public FieldPair getPair() {
        return pair;
    }

    public void setPair(FieldPair pair) {
        this.pair = pair;
    }

    public int getPosition() {
        return position;
    }

    protected void parse(String s) {
        String[] split = s.split(",");
        this.position = Integer.parseInt(split[1]);
        this.name = split[0];
    }

    public abstract Field executeFieldAction(FieldAction fieldAction);

    public String getName() {
        return name;
    }

    public String getHTMLDescription() {
        return "<span style='background-color: red'>" + this.getName() + "</span>";
    }
}
