public abstract class Chance {
    String name;
    String desc;
    GUI_Controller gui_controller;
    public void pullCard(Player p){};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}


