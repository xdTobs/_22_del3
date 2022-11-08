import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_main.GUI;

public class GUI_Controller {
    GUI gui;
    GUI_Player[] GUI_Players;
    GUI_Field[] fields;

    public GUI_Controller(GUI gui, GUI_Player[] GUI_Players, GUI_Field[] fields){
        this.gui = gui;
        this.GUI_Players = GUI_Players;
        this.fields = fields;
    }

}
