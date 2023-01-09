package entities.fields;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}