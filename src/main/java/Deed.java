import gui_fields.GUI_Field;
import gui_fields.GUI_Player;

public class Deed {
    GUI_Player owner;
    GUI_Field field;
    Deed pairedDeed;
    public Deed(GUI_Field field){
    this.field = field;
    }
    public Deed(GUI_Field field, GUI_Player owner, Deed pairedDeed){
        this.field = field;
        this.owner = owner;
        this.pairedDeed = pairedDeed;
    }



}
