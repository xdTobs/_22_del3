import gui_fields.GUI_Field;

public class moveChance extends Chance {
    GUI_Field field;


    public moveChance(GUI_Controller gui_controller,GUI_Field field){
        this.gui_controller = gui_controller;
        this.field = field;
        this.desc = l.moveTo+" "+field.getTitle();

    }

    @Override
    public void pullCard(Player p){

        p.getCar().setPosition(field);

    }


}
