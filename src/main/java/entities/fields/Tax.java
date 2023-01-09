package entities.fields;

public class Tax extends Field {
    int price;
    int percentPrice;
    public Tax(String s) {
        super(s);
        String[] split = s.split(",");
        if (Integer.parseInt(split[3])== 2000) {
            price = 2000;
            percentPrice = 0;
        }
       else{
            price = 2000;
            percentPrice = 10;
        }
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPercentPrice() {
        return percentPrice;
    }

    public void setPercentPrice(int percentPrice) {
        this.percentPrice = percentPrice;
    }

    @Override
    public Field executeFieldAction(FieldAction fieldAction) {
        fieldAction.taxAction(this);
        return null;
    }


}
