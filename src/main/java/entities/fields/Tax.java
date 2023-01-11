package entities.fields;

public class Tax extends Field {
    private int percentPrice;
    private final int fixedPrice;

    public Tax(String name, int percentageDeduction, int fixedPrice) {
        super(name);
        this.percentPrice = percentageDeduction;
        this.fixedPrice = fixedPrice;
//        String[] split = s.split(",");
//        if (Integer.parseInt(split[3])== 2000) {
//            price = 2000;
//            percentageDeduction = 0;
//        }
//       else{
//            price = 2000;
//            percentageDeduction = 10;
//        }
    }

    public int getFixedPrice() {
        return fixedPrice;
    }

    public int getPercentPrice() {
        return percentPrice;
    }

    @Override
    public Field executeFieldAction(FieldAction fieldAction) {
        fieldAction.taxAction(this);
        return null;
    }


}
