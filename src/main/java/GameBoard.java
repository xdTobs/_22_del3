import gui_fields.*;

import java.awt.*;

public class GameBoard {
    final private GUI_Field[] fields = new GUI_Field[24];
    private final int[] fieldValues = {1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 5, 5};

    public GameBoard(Language lang) {
        int streetIndex = 0;
        for (int i = 0; i < fields.length; i++) {
            if (i == 0) {
                fields[i] = new GUI_Start("Start", "Start", "Start", Color.white, Color.black);
            } else if ((i + 3) % 6 == 0) {
                // Chance field. 3, 9, 15, 21. Every sixth with an offset of three is chance field.
                fields[i] = new GUI_Chance();
            } else if (i == 6) {
                // Jail field
                fields[i] = new GUI_Jail();
            } else if (i == 12) {
                // Parking
                fields[i] = new GUI_Refuge();
            } else if (i == 18) {
                // Go to jail
                fields[i] = new GUI_Jail();
            } else {
                fields[i] = createStreet(streetIndex, lang);
                streetIndex++;
            }
        }
    }

    private GUI_Street createStreet(int streetIndex, Language lang) {
        GUI_Street street = new GUI_Street(lang.fieldNames[streetIndex], "" + fieldValues[streetIndex], "This is a street.", "" + fieldValues[streetIndex], Color.white, Color.BLACK);
        street.setOwnerName("Bank");
        return street;
    }

    public GUI_Field[] getFields() {
        return fields;
    }
}
