package entities.fields;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {
    Field field;

    public static Field getStartFieldDebug() {
        return new Start("Start,0, start,,,,,,,,");
    }

    @BeforeEach
    void setUp() {
        field = new Start("Start,0, start,,,,,,,,");
    }


    @Test
    void getPosition() {
        assertEquals(0, field.getPosition());
    }

    @Test
    void playerOwnsAllFieldsInFieldPair() {
        List<RentableField> list = new ArrayList<>();

        list.add(new Street("Rødovrevej,1, street,1200,1000,50,250,750,2250,4000,6000"));
        list.add(new Street("Rødovrevej,2, street,1200,1000,50,250,750,2250,4000,6000"));

        list.add(new Street("Rødovrevej,5, street,1200,1000,50,250,750,2250,4000,6000"));
        list.add(new Street("Rødovrevej,6, street,1200,1000,50,250,750,2250,4000,6000"));

        FieldPair pair0 = new FieldPair(Color.BLUE, new int[]{1, 2});
        list.get(0).setPair(pair0);
        list.get(1).setPair(pair0);

        FieldPair pair1 = new FieldPair(Color.GREEN, new int[]{5, 6});
        list.get(2).setPair(pair1);
        list.get(3).setPair(pair1);

        List<RentableField> playerOwned = new ArrayList<>();
        playerOwned.add(list.get(0));
        playerOwned.add(list.get(1));
        playerOwned.add(list.get(2));
        playerOwned.add(list.get(3));

        var set = Field.findFieldPairsAllOwnedByPlayer(playerOwned);
        assertEquals(1, set.size());
    }
}