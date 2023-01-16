package entities.fields;

import entities.Player;

import java.util.Arrays;

public abstract class RentableField extends Field {
    int price;
    int[] rents;
    private Player owner = null;
    boolean pawned;

    public RentableField(String name, int price, int[] rents) {
        super(name);
        this.price = price;
        this.rents = rents;
        pawned = false;
    }

    public static int[] parseRent(String line) {
        int[] rents = new int[6];
        String[] split = line.split(",");
        String[] arr = Arrays.copyOfRange(split, 5, 11);
        for (int i = 0; i < arr.length; i++) {
            if (!arr[i].trim().equals("")) {
                rents[i] = Integer.parseInt(arr[i]);
            }
        }
        return rents;
    }

    public int getPrice() {
        return price;
    }


    public int getRent(int i) {
        return rents[i];
    }

    public Player getOwner() {
        return owner;
    }

    public boolean isNotOwned() {
        return owner == null;
    }
    public boolean isOwned() {
        return owner != null;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public boolean isPawned() {
        return pawned;
    }

    public void setPawned(boolean pawned) {
        this.pawned = pawned;
    }
}
