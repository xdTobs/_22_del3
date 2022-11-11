import gui_fields.GUI_Field;

public class moveChance extends Chance {
    GUI_Field field;
    int id;

    public moveChance(GUI_Controller gui_controller,int id){
        this.gui_controller = gui_controller;
        this.field = gui_controller.getFields()[id];
        this.desc = l.moveTo+" "+field.getTitle();
        this.id = id;
    }

    @Override
    public void pullCard(Player p){
        p.setPos(id);
        p.getCar().setPosition(field);

    }


}
