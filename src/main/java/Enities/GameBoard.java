package Enities;

import Enities.Fields.Field;
import Enities.Fields.Street;
import gui_fields.*;

import java.awt.*;

public class GameBoard {
    //    final private GUI_Field[] fields = new GUI_Field[24];
    final private Field[] fields = new Field[24];

    public GameBoard() {
        int streetIndex = 0;
        for (int i = 0; i < fields.length; i++) {
            if (i == 0) {
                this.fields[i] = new Field(i, new GUI_Start("Start", "Start", "Start", Color.white, Color.black));
            } else if ((i + 3) % 6 == 0) {
                // ChanceCards.Chance field. 3, 9, 15, 21. Every sixth with an offset of three is chance field.
                this.fields[i] = new Field(i, new GUI_Chance());
            } else if (i == 6) {
                // Jail field
                this.fields[i] = new Field(i, new GUI_Jail());
            } else if (i == 12) {
                // Parking
                this.fields[i] = new Field(i, new GUI_Refuge());
            } else if (i == 18) {
                // Go to jail
                this.fields[i] = new Field(i, new GUI_Jail());
            } else {
//                GUI_Street guiStreet = createStreet(i);
                this.fields[i] = new Street(i);
            }
        }
    }

    public GUI_Field[] getGuiFields() {
        GUI_Field[] guiFields = new GUI_Field[fields.length];
        for (int i = 0; i < fields.length; i++) {
            guiFields[i] = fields[i].getGuiField();
        }
        return guiFields;
    }

    public Field getField(int position) {
        return fields[position];
    }
}
