package se.iths.alfred.webshop.model;

public abstract class Discount {

    protected String description;

    public Discount(String description) {
        this.description = description;
    }

    public void readDiscount(String discountCode) {
        Discount discount = null;

        if (discountCode.equals("halfprice")) {
            discount = new HalfPriceDiscount("50% rabatt");
        } else {
            discount = new TenPercentDiscount("10% rabatt");
        }
    }

    public abstract double calculatePrice(double originalPrice);

    //---------- Getters & Setters ----------
    public String getDescription() {
        return description;
    }
}
