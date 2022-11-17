package Enities.Fields;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {
    Field field;

    @BeforeEach
    void setUp() {
        field = new Field(3);
    }

    @Test
    void getGuiField() {
        assertNotNull(field.getGuiField());
    }

    @Test
    void getPosition() {
        assertEquals(3, field.getPosition());
    }
}