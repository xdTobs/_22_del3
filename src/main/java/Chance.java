public abstract class Chance {

    String desc;
    GUI_Controller gui_controller;
    public void pullCard(Player p){};

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}


