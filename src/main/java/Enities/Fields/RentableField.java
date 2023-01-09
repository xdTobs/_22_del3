package Enities.Fields;

import java.util.Arrays;

public abstract class RentableField extends Field {
    int price;
    int housePrice;
    int houses = 0;
    private int[] rent = new int[6];
    private String ownerName = "Bank";

    public RentableField(String s) {
        super(s);
        String[] split = s.split(",");
        for (int i = 0; i < split.length - 6; i++) {
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

    public void BuyProperty(){

    }

    public int getRent(int i) {
        return rent[i];
    }

    public String getOwner() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

}
