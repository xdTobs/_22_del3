package Enities.Fields;

import gui_fields.GUI_Field;
import gui_fields.GUI_Street;

public class Street extends Field {
    public Street(int position) {
        super(position, new GUI_Street());
        String rent = "" + calculateRent(position);
        GUI_Street street = (GUI_Street) super.getGuiField();
        street.setRent(rent);
        street.setSubText(rent);
        street.setOwnerName("Bank");
    }

    public int getRent() {
        return Integer.parseInt(getGuiStreet().getRent());
    }

    public String getOwnerName() {
        return getGuiStreet().getOwnerName();
    }

    public void setOwnerName(String ownerName) {
        getGuiStreet().setOwnerName(ownerName);
    }

    private GUI_Street getGuiStreet() {
        return (GUI_Street) super.getGuiField();
    }


    private int calculateRent(int i) {
        if (i < 6) {
            return 1;
        } else if (i < 12) {
            return 2;
        } else if (i < 18) {
            return 3;
        } else if (i < 21) {
            return 4;
        } else {
            return 5;
        }
    }

}
