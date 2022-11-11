import gui_fields.GUI_Field;
import gui_fields.GUI_Street;

public class moveGetChance extends Chance {
    GUI_Street street;
    DiceGame game;

    public moveGetChance(GUI_Controller gui_controller, GUI_Street street,DiceGame game ){
        this.gui_controller = gui_controller;
        this.street = street;
        this.desc = l.moveTo+" "+street.getTitle();
        this.game = game;

    }

    @Override
    public void pullCard(Player p){

        p.getCar().setPosition(street);
        if (street.getOwnerName().equals("Bank")){
            p.setBalance(p.getBalance()+Integer.parseInt(street.getRent()),gui_controller);
            game.onStreet(street,p);
        }
        else game.onStreet(street,p);


    }
}
