import gui_fields.GUI_Field;
import gui_fields.GUI_Street;

public class pickStreetChance extends Chance {
    private GUI_Street[] choices;
    private String[] options;
    private DiceGame game;


    public pickStreetChance(String desc, GUI_Controller gui_controller, GUI_Street[] choices,DiceGame game){
        this.gui_controller = gui_controller;
        this.desc = desc;
        this.choices = choices;
        this.game = game;
        options = new String[choices.length];
        for (int i = 0; i < choices.length; i++) {
            options[i] = choices[i].getTitle();
        }

    }
    public void pullCard(Player p){

        String s =gui_controller.getGui().getUserSelection(l.onChanceCard+" "+l.onPickFieldChance, options);
        for (int i = 0; i < options.length; i++) {
            if (options[i].equals(s)){
                moveGetChance temp = new moveGetChance(gui_controller,choices[i],game);
                temp.pullCard(p);
                break;
            }
        }
        System.out.println(s);

    }

}
