package Enities.Fields;

public abstract class RentableField extends Field{
    private final int[] rent = new int[6];
    private String ownerName = "Bank";
    private int price;
    private int housePrice;

    public RentableField(String s) {
        super(s);
        String[] split = s.split(",");
        for (int i = 0; i < split.length-6; i++) {
            this.rent[i] = Integer.parseInt(split[i + 5]);
        }
        this.price = Integer.parseInt(split[3]);
        this.housePrice = Integer.parseInt(split[4]);
    }

    public int getPrice() {
        return price;
    }

    public int getHousePrice() {
        return housePrice;
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
