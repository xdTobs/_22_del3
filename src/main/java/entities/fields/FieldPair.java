package entities.fields;

import java.awt.*;

/**
 * Contains info about the color of streets and fields that belong together.
 */
public class FieldPair {
    // todo fields har styr pa farger.

    private Color backgroundColor;
    private Color foregroundColor;


    private int[] fieldIds;

    public FieldPair(Color backgroundColor, int[] fieldIds) {
        this(backgroundColor, Color.BLACK, fieldIds);
    }

    public FieldPair(Color backgroundColor, Color foregroundColor, int[] fieldIds) {
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
        this.fieldIds = fieldIds;
    }

    public int[] getFieldIds() {
        return fieldIds;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getForegroundColor() {
        return foregroundColor;
    }
}
