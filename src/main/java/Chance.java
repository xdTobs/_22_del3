public abstract class Chance {

    protected String desc;
    protected GUI_Controller gui_controller;
    public void pullCard(Player p){};

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}


