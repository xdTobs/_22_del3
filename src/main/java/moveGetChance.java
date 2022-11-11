import gui_fields.GUI_Field;
import gui_fields.GUI_Street;

public class moveGetChance extends Chance {
    GUI_Street street;
    DiceGame game;
    int id;

    public moveGetChance(GUI_Controller gui_controller,int id,DiceGame game ){
        this.gui_controller = gui_controller;
        this.street = (GUI_Street) gui_controller.getFields()[id];
        this.desc = l.moveTo+" "+street.getTitle();
        this.game = game;
        this.id = id;
    }

    @Override
    public void pullCard(Player p){
        p.setPos(id);
        p.getCar().setPosition(street);
        if (street.getOwnerName().equals("Bank")){
            p.setBalance(p.getBalance()+Integer.parseInt(street.getRent()),gui_controller);
            game.onStreet(street,p);
        }
        else game.onStreet(street,p);


    }
}
