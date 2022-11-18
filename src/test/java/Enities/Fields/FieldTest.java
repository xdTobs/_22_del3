package Enities.Fields;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {
    Field field;

    @BeforeEach
    void setUp() {
        field = new Start();
    }


    @Test
    void getPosition() {
        assertEquals(0, field.getPosition());
    }
}