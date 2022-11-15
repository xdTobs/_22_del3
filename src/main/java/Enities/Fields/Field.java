package Enities.Fields;

import Language.LanguageHandler;
import gui_fields.*;

import java.awt.*;

public class Field {
    private GUI_Field guiField;
    private int position;

    public Field(int position, GUI_Field guiField) {
        this.position = position;
        this.guiField = guiField;
        guiField.setTitle(LanguageHandler.getFieldName(position));
    }


    public GUI_Field getGuiField() {
        return guiField;
    }

    public int getPosition() {
        return position;
    }

}
