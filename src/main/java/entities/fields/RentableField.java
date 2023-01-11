package entities.fields;

import entities.Player;

public abstract class RentableField extends Field {
    int price;
    int housePrice;
    int houses = 0;
    private int[] rent = new int[6];
    private Player owner = null;

    public RentableField(String s) {
        super(s);
        String[] split = s.split(",");
        for (int i = 0; i < split.length - 5; i++) {
            this.rent[i] = Integer.parseInt(split[i + 5]);
        }
        price = Integer.parseInt(split[3]);
        housePrice = Integer.parseInt(split[4]);
    }

    public int getHouses() {
        return houses;
    }

    public void setHouses(int houses) {
        this.houses = houses;
    }

    public int getPrice() {
        return price;
    }

    public int getHousePrice() {
        return housePrice;
    }

    //TODO skal vi lave de samme metoder for "Hotels"?

    public void BuyProperty() {

    }

    public int getBaseRent(int i) {
        return rent[i];
    }

    public Player getOwner() {
        return owner;
    }

    public boolean isNotOwned() {
        return owner == null;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

}
